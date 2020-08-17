<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <title>Knowledge Package Sets</title>
    <script type="text/javascript" src="resources/grid_gpl/codebase/grid.js?v=6.5.2"></script>
    <link rel="stylesheet" href="resources/grid_gpl/codebase/grid.css?v=6.5.2">
    <link rel="stylesheet" href="resources/grid_gpl/index.css?v=6.5.2">
    <link rel="stylesheet" href="resources/html-table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <form name="set" method = "POST" action = "sets">
        <table class="form-right">
            <tr>
               <td>Title</td>
               <td><input style="width:300px" title="Title" type="text" name="title" maxlength="250" required="required"></td>
            </tr>
            <tr>
               <td>Knowledge Packages<br>(use ctrl)</td>
               <td>
                  <select name = "kpacs" multiple=true size=5>
                    <c:forEach var="kpac" items="${kpacs}">
                       <option value=${kpac.id}>${kpac.title}</option>
                    </c:forEach>
                  </select>
               </td>
            </tr>
            <tr>
               <td colspan="2"><input type = "submit" value = "Add"/></td>
            </tr>
        <table>
    </form>

    <section class="dhx_sample-container">
        <div class="dhx_sample-container__widget" id="grid"></div>
    </section>

    <script>
        var dataset = ${sets}
        var grid = new dhx.Grid("grid", {
          columns: [
            { width: 60, id: "id", header: [{ text: "ID", align: "center" }],  },
            { width: 1050, id: "title", header: [{ text: "Title", align: "center" }] },
            { width: 100, id: "action", header: [{ text: "Delete", align: "center" }],
                sortable: false,
                htmlEnable: true,
                autoWidth: true,
                align: "center",
                template: function (text, row, col) {
                     for(var d in dataset){
                         if(dataset[d]['id'] == row.id ){
                            return "<form action=\"sets/delete/" + row.id + "\" method=\"get\"><button style=\"margin-top: 5px\" type=\"submit\" name=\"id\"><i style=\"font-size:24px\" class=\"fa\">&#xf014;</i></button></form>"
                         }
                     }
                }
             }
          ],
          data: dataset
        });

        grid.events.on("CellDblClick", function(row,column,e){
              var win = window.open('set/' + row.id, '_blank');
              win.focus();
        });
    </script>
</body>
</html>
