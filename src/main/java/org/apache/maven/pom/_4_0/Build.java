//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2017.02.04 um 10:04:23 PM CET 
//


package org.apache.maven.pom._4_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 *         
 *         The <code>&lt;build&gt;</code> element contains informations required to build the project.
 *         Default values are defined in Super POM.
 *         
 *       
 * 
 * <p>Java-Klasse für Build complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="Build">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="sourceDirectory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="scriptSourceDirectory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testSourceDirectory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="outputDirectory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testOutputDirectory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="extensions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="extension" type="{http://maven.apache.org/POM/4.0.0}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="defaultGoal" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resources" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="resource" type="{http://maven.apache.org/POM/4.0.0}Resource" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="testResources" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="testResource" type="{http://maven.apache.org/POM/4.0.0}Resource" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="directory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="finalName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filters" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="filter" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pluginManagement" type="{http://maven.apache.org/POM/4.0.0}PluginManagement" minOccurs="0"/>
 *         &lt;element name="plugins" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="plugin" type="{http://maven.apache.org/POM/4.0.0}Plugin" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Build", propOrder = {

})
public class Build {

    protected String sourceDirectory;
    protected String scriptSourceDirectory;
    protected String testSourceDirectory;
    protected String outputDirectory;
    protected String testOutputDirectory;
    protected Extensions extensions;
    protected String defaultGoal;
    protected Resources resources;
    protected TestResources testResources;
    protected String directory;
    protected String finalName;
    protected Filters filters;
    protected PluginManagement pluginManagement;
    protected Plugins plugins;

    /**
     * Ruft den Wert der sourceDirectory-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSourceDirectory() {
        return sourceDirectory;
    }

    /**
     * Legt den Wert der sourceDirectory-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSourceDirectory(String value) {
        this.sourceDirectory = value;
    }

    /**
     * Ruft den Wert der scriptSourceDirectory-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getScriptSourceDirectory() {
        return scriptSourceDirectory;
    }

    /**
     * Legt den Wert der scriptSourceDirectory-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setScriptSourceDirectory(String value) {
        this.scriptSourceDirectory = value;
    }

    /**
     * Ruft den Wert der testSourceDirectory-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTestSourceDirectory() {
        return testSourceDirectory;
    }

    /**
     * Legt den Wert der testSourceDirectory-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTestSourceDirectory(String value) {
        this.testSourceDirectory = value;
    }

    /**
     * Ruft den Wert der outputDirectory-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Legt den Wert der outputDirectory-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setOutputDirectory(String value) {
        this.outputDirectory = value;
    }

    /**
     * Ruft den Wert der testOutputDirectory-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTestOutputDirectory() {
        return testOutputDirectory;
    }

    /**
     * Legt den Wert der testOutputDirectory-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTestOutputDirectory(String value) {
        this.testOutputDirectory = value;
    }

    /**
     * Ruft den Wert der extensions-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Extensions }
     *
     */
    public Extensions getExtensions() {
        return extensions;
    }

    /**
     * Legt den Wert der extensions-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Extensions }
     *
     */
    public void setExtensions(Extensions value) {
        this.extensions = value;
    }

    /**
     * Ruft den Wert der defaultGoal-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDefaultGoal() {
        return defaultGoal;
    }

    /**
     * Legt den Wert der defaultGoal-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDefaultGoal(String value) {
        this.defaultGoal = value;
    }

    /**
     * Ruft den Wert der resources-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Resources }
     *
     */
    public Resources getResources() {
        return resources;
    }

    /**
     * Legt den Wert der resources-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Resources }
     *
     */
    public void setResources(Resources value) {
        this.resources = value;
    }

    /**
     * Ruft den Wert der testResources-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link TestResources }
     *
     */
    public TestResources getTestResources() {
        return testResources;
    }

    /**
     * Legt den Wert der testResources-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link TestResources }
     *
     */
    public void setTestResources(TestResources value) {
        this.testResources = value;
    }

    /**
     * Ruft den Wert der directory-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Legt den Wert der directory-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setDirectory(String value) {
        this.directory = value;
    }

    /**
     * Ruft den Wert der finalName-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFinalName() {
        return finalName;
    }

    /**
     * Legt den Wert der finalName-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFinalName(String value) {
        this.finalName = value;
    }

    /**
     * Ruft den Wert der filters-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Filters }
     *
     */
    public Filters getFilters() {
        return filters;
    }

    /**
     * Legt den Wert der filters-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Filters }
     *
     */
    public void setFilters(Filters value) {
        this.filters = value;
    }

    /**
     * Ruft den Wert der pluginManagement-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link PluginManagement }
     *
     */
    public PluginManagement getPluginManagement() {
        return pluginManagement;
    }

    /**
     * Legt den Wert der pluginManagement-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link PluginManagement }
     *
     */
    public void setPluginManagement(PluginManagement value) {
        this.pluginManagement = value;
    }

    /**
     * Ruft den Wert der plugins-Eigenschaft ab.
     *
     * @return
     *     possible object is
     *     {@link Plugins }
     *
     */
    public Plugins getPlugins() {
        return plugins;
    }

    /**
     * Legt den Wert der plugins-Eigenschaft fest.
     *
     * @param value
     *     allowed object is
     *     {@link Plugins }
     *
     */
    public void setPlugins(Plugins value) {
        this.plugins = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="extension" type="{http://maven.apache.org/POM/4.0.0}Extension" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "extension"
    })
    public static class Extensions {

        protected List<Extension> extension;

        /**
         * Gets the value of the extension property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the extension property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExtension().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Extension }
         * 
         * 
         */
        public List<Extension> getExtension() {
            if (extension == null) {
                extension = new ArrayList<Extension>();
            }
            return this.extension;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="filter" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "filter"
    })
    public static class Filters {

        protected List<String> filter;

        /**
         * Gets the value of the filter property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the filter property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFilter().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getFilter() {
            if (filter == null) {
                filter = new ArrayList<String>();
            }
            return this.filter;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="plugin" type="{http://maven.apache.org/POM/4.0.0}Plugin" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "plugin"
    })
    public static class Plugins {

        protected List<Plugin> plugin;

        /**
         * Gets the value of the plugin property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the plugin property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPlugin().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Plugin }
         * 
         * 
         */
        public List<Plugin> getPlugin() {
            if (plugin == null) {
                plugin = new ArrayList<Plugin>();
            }
            return this.plugin;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="resource" type="{http://maven.apache.org/POM/4.0.0}Resource" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "resource"
    })
    public static class Resources {

        protected List<Resource> resource;

        /**
         * Gets the value of the resource property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the resource property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getResource().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Resource }
         * 
         * 
         */
        public List<Resource> getResource() {
            if (resource == null) {
                resource = new ArrayList<Resource>();
            }
            return this.resource;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="testResource" type="{http://maven.apache.org/POM/4.0.0}Resource" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "testResource"
    })
    public static class TestResources {

        protected List<Resource> testResource;

        /**
         * Gets the value of the testResource property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the testResource property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTestResource().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Resource }
         * 
         * 
         */
        public List<Resource> getTestResource() {
            if (testResource == null) {
                testResource = new ArrayList<Resource>();
            }
            return this.testResource;
        }

    }

}
