<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	  xmlns:xades="http://uri.etsi.org/01903/v1.3.2#"
	  xmlns:ds="http://www.w3.org/2000/09/xmldsig#">

    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>
	<!--  <xsl:strip-space elements="*"/> -->
    <!-- Match the root element and apply templates -->
     <xsl:template match="node() | @*">
        <xsl:copy-of select="//xades:SignedProperties"/>
    </xsl:template>
    
</xsl:stylesheet>