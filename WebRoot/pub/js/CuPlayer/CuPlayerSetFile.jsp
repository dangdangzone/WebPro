<%
	String pageUrl = request.getParameter("pageUrl");
%>
<content>
  <CuPlayerWidth>1000</CuPlayerWidth>
  <CuPlayerHeight>500</CuPlayerHeight>
  <CuPlayerAutoPlay>yes</CuPlayerAutoPlay>    
  <CuPlayerFile><%=pageUrl %></CuPlayerFile>
</content>