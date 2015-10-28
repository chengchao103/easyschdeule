/*
 * Copyright(C) 2010-2012 Alibaba Group Holding Limited
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2 as
 *  published by the Free Software Foundation.
 *
 */
package com.taobao.ad.easyschedule.security;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class ActionSecurityBean implements InitializingBean, ApplicationContextAware {
	String encoding = "UTF-8";
	String[] beanNames = new String[] { "*Action" };

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public void setBeanNames(String[] beanNames) {
		this.beanNames = beanNames;
	}

	private Resource xml;
	private List<MenuItem> menu;
	private Map<Long, Module> moduleIndex = new HashMap<Long, Module>();
	private Map<Method, Module> methodIndex = new HashMap<Method, Module>();

	/**
	 * 根据ID获取功能模块对象
	 * 
	 * @param id
	 *            模块ID
	 * @return
	 */
	public Module getModule(Long id) {
		return moduleIndex.get(id);
	}

	/**
	 * 返回所有操作，供检查调用等
	 * 
	 * @return Set<Method>
	 */
	public Set<Method> getOperationMethodSet() {
		return Collections.unmodifiableSet(methodIndex.keySet());
	}

	/**
	 * 根据传入的功能模块(Feature) ID，返回具有访问权限的菜单子树
	 * 
	 * @param moduleIds
	 * @return
	 */
	public List<MenuItem> getMenu(Collection<Long> moduleIds) {
		if (moduleIds == null || moduleIds.isEmpty()) {
			return Collections.emptyList();
		}
		Map<Long, Long> ids = new HashMap<Long, Long>();
		for (Long id : moduleIds) {
			ids.put(id, null);
		}
		List<MenuItem> subtree = new LinkedList<MenuItem>();
		List<MenuItem> menu = this.getMenu();
		for (MenuItem item : menu) {
			if (item.getItems() != null) {
				List<MenuItem> submenu = new LinkedList<MenuItem>();
				for (MenuItem subitem : item.getItems()) {
					boolean add = false;
					MenuItem newItem = new MenuItem(subitem.getName(), subitem.getLink(), subitem.getTarget());
					if (subitem.getModules() != null && subitem.state == 0) {
						for (Module module : subitem.getModules()) {
							if (ids.containsKey(module.getId())) {
								add = true;
								newItem.addModule(module);
							}
						}
					} else if (subitem.state == 1 || subitem.getModules() == null) {
						add = true;
					}

					if (add) {
						submenu.add(newItem);
					}
				}
				if (!submenu.isEmpty()) {
					MenuItem newItem = new MenuItem(item.getName());
					newItem.addSubItems(submenu);
					subtree.add(newItem);
				}
			}
		}
		return subtree;
	}

	/**
	 * 根据角色所拥有的Module，判断是否具有访问某方法的权限
	 * 
	 * @return List<MenuItem>
	 */
	public boolean hasAccess(Collection<Integer> moduleIds, Method method) {
		if (!methodIndex.containsKey(method)) {
			return true;
		}
		if (moduleIds == null) {
			return false;
		}
		Module module = methodIndex.get(method);
		for (Integer id : moduleIds) {
			if (id.intValue() == module.getId()) {
				return true;
			}
		}
		return false;
	}

	public void setXml(Resource resource) {
		if (resource == null) {
			throw new IllegalArgumentException("Can't load menu configration file");
		}
		this.xml = resource;
		this.getMenu();
	}

	private long lastModified = 0;
	private Object lock = new Object();

	/**
	 * 获取菜单树
	 * 
	 * @throws Exception
	 */
	public List<MenuItem> getMenu() {
		synchronized (lock) {
			long thisModified;
			try {
				thisModified = xml.getFile().lastModified();
			} catch (IOException e) {
				throw new IllegalArgumentException("Can't find menu configration file");
			}
			if (lastModified == thisModified) {
				return menu;
			}
			// System.out.println("load new menu.");
			lastModified = thisModified;
			try {
				menu = Collections.unmodifiableList(readMenuFromFile());
				return menu;
			} catch (Exception e) {
				// 新的菜单读取失败，用修改前的菜单配置。
				System.out.println("load new menu fail,use old menu config.");
				return menu;
			}
		}
	}

	/**
	 * 从配置文件中载入菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<MenuItem> readMenuFromFile() throws Exception {
		List<MenuItem> tmpMenu;
		Map<Long, Module> tmpModuleIndex;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(new InputSource(new InputStreamReader(xml.getInputStream(), encoding)));
			NodeList items = dom.getElementsByTagName("item");
			tmpMenu = new LinkedList<MenuItem>();
			tmpModuleIndex = new HashMap<Long, Module>();
			MenuItem currentTopMenu = null;
			for (int i = 0; i < items.getLength(); i++) {
				Node item = items.item(i);
				String name = item.getAttributes().getNamedItem("name").getNodeValue();
				String link = "#";
				if (item.getAttributes().getNamedItem("link") != null)
					link = item.getAttributes().getNamedItem("link").getNodeValue();
				Node parent = item.getParentNode();
				if ("menu".equals(parent.getNodeName())) {
					currentTopMenu = new MenuItem(name, link);
					tmpMenu.add(currentTopMenu);
				} else if ("item".equals(parent.getNodeName())) {
					MenuItem subMenu = new MenuItem(name);
					currentTopMenu.addSubItem(subMenu); // add the sub node to
					// parent node
					if (item.getAttributes().getNamedItem("link") != null)
						subMenu.link = item.getAttributes().getNamedItem("link").getNodeValue();
					if (item.getAttributes().getNamedItem("target") != null)
						subMenu.target = item.getAttributes().getNamedItem("target").getNodeValue();
					if (item.getAttributes().getNamedItem("state") != null) {
						if ("show".equalsIgnoreCase(item.getAttributes().getNamedItem("state").getNodeValue())) {
							subMenu.state = 1;
						} else if ("hide".equalsIgnoreCase(item.getAttributes().getNamedItem("state").getNodeValue())) {
							subMenu.state = -1;
						}
					}

					setModules(subMenu, item, tmpModuleIndex);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		this.moduleIndex = tmpModuleIndex;
		return tmpMenu;
	}

	private void setModules(MenuItem menuItem, Node item, Map<Long, Module> tmpModuleIndex) {
		NodeList children = item.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if ("module".equals(node.getNodeName())) {
				// System.out.println("  "+node.getAttributes().getNamedItem("id"));
				menuItem.addModule(new Module(Long.parseLong(node.getAttributes().getNamedItem("id").getNodeValue()), node.getAttributes().getNamedItem("name")
						.getNodeValue(), tmpModuleIndex));
			}
		}
	}

	/**
	 * 注意此方法会在所有Spring管理的bean都初始化好了后才开始调用， 所以如果扫描所有BO有缺少，可以把逻辑放这个方法内实现
	 * 
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 扫描所有的BO，根据Annotation，把操作方法及相应的Annotation加到对应的模块中去
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		if (beanNames == null || beanNames.length == 0) {
			System.err.println("Warning: seems there are no Spring Beans defined, please double check..................");
			return;
		}
		for (String name : beanNames) {
			if (!name.endsWith("Action")) {
				continue;
			}
			Class<?> c = AopUtils.getTargetClass(applicationContext.getBean(name));
			Object bean = applicationContext.getBean(name);

			while (Proxy.isProxyClass(c) && (bean instanceof Advised)) {
				try {
					bean = ((Advised) bean).getTargetSource();
					bean = ((TargetSource) bean).getTarget();
					c = bean.getClass();
				} catch (Exception e) {
					throw new IllegalStateException("Can't initialize SecurityBean, due to:" + e.getMessage());
				}
			}
			long moduleIdDefinedInInterface = -1;
			ActionSecurity moduleAnno = AnnotationUtils.findAnnotation(c, ActionSecurity.class);
			if (moduleAnno != null) {
				moduleIdDefinedInInterface = moduleAnno.module();
			}
			Method[] methods = c.getDeclaredMethods();
			for (Method method : methods) {
				ActionSecurity methodAnno = AnnotationUtils.findAnnotation(method, ActionSecurity.class);
				if (methodAnno != null || moduleIdDefinedInInterface != -1) {
					if (methodAnno == null) {
						methodAnno = moduleAnno; // use interface annotation
					}
					long module = methodAnno.module() == 0 ? moduleIdDefinedInInterface : methodAnno.module();
					Module myModule = moduleIndex.get(module);
					if (myModule == null) {
						throw new IllegalArgumentException("Found invalid module id:" + module + " in method annotation:" + method + " valid module ids are: "
								+ moduleIndex.keySet());
					}
					if (methodAnno.enable()) {
						myModule.addOperation(method, methodAnno, methodIndex);
					}
				}
			}
		}
		System.out.println("[ActionSecurityBean] Total " + methodIndex.size() + " methods are secured!");
	}

	public class MenuItem {
		String name;
		String link;
		String target;
		short state = 0;
		List<MenuItem> items;
		List<Module> modules;

		public String getTarget() {
			return target;
		}

		public void setTarget(String target) {
			this.target = target;
		}

		public void addSubItem(MenuItem item) {
			if (items == null) {
				items = new LinkedList<MenuItem>();
			}
			items.add(item);
		}

		public void addSubItems(List<MenuItem> addItems) {
			if (items == null) {
				items = new ArrayList<MenuItem>(addItems.size());
			}
			items.addAll(addItems);
		}

		public MenuItem(String name, String link) {
			this.name = name;
			this.link = link;
		}

		public MenuItem(String name, String link, String target) {
			this.name = name;
			this.link = link;
			this.target = target;
		}

		public void addModule(Module module) {
			if (modules == null) {
				modules = new LinkedList<Module>();
			}
			modules.add(module);
		}

		public MenuItem(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}

		public List<MenuItem> getItems() {
			return items;
		}

		public void setItems(List<MenuItem> items) {
			this.items = items;
		}

		public List<Module> getModules() {
			return modules;
		}

		public void setModules(List<Module> modules) {
			this.modules = modules;
		}

		public boolean isTopMenu() {
			return items != null && !items.isEmpty();
		}

		@Override
		public String toString() {
			if (isTopMenu()) {
				return "menu: " + name + "\n\t\tsub menu:" + items + "\n";
			} else {
				return name + " " + link + "\n\t\t\t\tmodules:" + modules + "\n";
			}
		}
	}

	/**
	 * 功能模块，一个角色可以拥有多个功能模块的访问权限
	 */
	public class Module {
		long id;
		String name;
		Map<Method, ActionSecurity> operations;

		public Module(long id, String name, Map<Long, Module> moduleIndex) {
			if (moduleIndex.containsKey(id)) {
				// System.out.println(">>>"+id);
				throw new IllegalArgumentException("Found duplicate module id:" + id);
			}
			this.id = id;
			this.name = name;
			moduleIndex.put(id, this);
		}

		public long getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void addOperation(Method m, ActionSecurity s, Map<Method, Module> methodIndex) {
			if (!s.enable()) {
				return;
			}
			if (operations == null) {
				operations = new HashMap<Method, ActionSecurity>();
			}
			methodIndex.put(m, this);
			operations.put(m, s);
		}

		@Override
		public String toString() {
			return "id:" + id + " " + name + "\n\t\t\t\t methods:" + operations + "\n";
		}
	}
}
