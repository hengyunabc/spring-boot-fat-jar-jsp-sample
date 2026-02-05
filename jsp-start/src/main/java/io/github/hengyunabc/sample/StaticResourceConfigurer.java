package io.github.hengyunabc.sample;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.WebResourceRoot.ResourceSetType;
import org.springframework.boot.system.ApplicationHome;

/**
 * Add main class fat jar/exploded directory into tomcat ResourceSet.
 *
 * @author hengyunabc 2017-07-29
 *
 */
public class StaticResourceConfigurer implements LifecycleListener {

	private static final String META_INF_RESOURCES = "META-INF/resources";
	private static final String INTERNAL_PATH = "/" + META_INF_RESOURCES;
	private static final String[] MARKER_RESOURCES = { "jsp/home.jsp", "jsp/hello.jsp" };

	private final Context context;

	StaticResourceConfigurer(Context context) {
		this.context = context;
	}

	@Override
	public void lifecycleEvent(LifecycleEvent event) {
		if (!Lifecycle.BEFORE_INIT_EVENT.equals(event.getType())) {
			return;
		}

		File resourceBase = findMetaInfResourcesBase(new ApplicationHome(getClass()).getSource());
		if (resourceBase == null) {
			return;
		}
		this.context.getResources().createWebResourceSet(ResourceSetType.RESOURCE_JAR, "/", toUrl(resourceBase),
				INTERNAL_PATH);
	}

	private static File findMetaInfResourcesBase(File source) {
		if (source == null) {
			return null;
		}
		if (hasMarkerResources(source)) {
			return source;
		}
		File root = getBootInfRoot(source);
		if (root != null && hasMarkerResources(root)) {
			return root;
		}
		return null;
	}

	private static File getBootInfRoot(File source) {
		if (!source.isDirectory() || !"classes".equals(source.getName())) {
			return null;
		}
		File bootInf = source.getParentFile();
		if (bootInf == null || !"BOOT-INF".equals(bootInf.getName())) {
			return null;
		}
		return bootInf.getParentFile();
	}

	private static boolean hasMarkerResources(File source) {
		if (source.isFile()) {
			return hasMarkerResourcesInJar(source);
		}
		if (!source.isDirectory()) {
			return false;
		}
		File resourcesRoot = new File(source, META_INF_RESOURCES);
		if (!resourcesRoot.isDirectory()) {
			return false;
		}
		for (String marker : MARKER_RESOURCES) {
			if (new File(resourcesRoot, marker).isFile()) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasMarkerResourcesInJar(File jarFile) {
		try (JarFile jar = new JarFile(jarFile)) {
			for (String marker : MARKER_RESOURCES) {
				if (jar.getJarEntry(META_INF_RESOURCES + "/" + marker) != null) {
					return true;
				}
			}
		} catch (IOException ignored) {
			return false;
		}
		return false;
	}

	private static URL toUrl(File file) {
		try {
			return file.toURI().toURL();
		} catch (IOException e) {
			throw new IllegalStateException("Can not add tomcat resources", e);
		}
	}
}
