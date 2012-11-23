<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>
<jsp:useBean id="sf" class="specflow.server.SpecFlowBean" scope="request"/>

<div class="parameter">
    NUnit-Console.exe: <strong><props:displayValue name="${sf.NUnitConsoleExe}"/></strong>
</div>