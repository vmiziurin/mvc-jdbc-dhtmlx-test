<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <%@ page isELIgnored="false" %>
    <title>Knowledge Packages</title>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
    <script type="text/javascript" src="resources/grid_gpl/codebase/grid.js?v=6.5.2"></script>
    <link rel="stylesheet" href="resources/grid_gpl/codebase/grid.css?v=6.5.2">
    <link rel="stylesheet" href="resources/grid_gpl/index.css?v=6.5.2">
    <link rel="stylesheet" href="resources/html-table.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
    <form name = "kpac" action="kpacs" method="post">
        <table class="form-right">
            <td colspan="2">Add new Knowledge Package</td></tr>
            <tr>
               <td>Title</td>
               <td><input style="width:100%" title="Title" type="text" name="title" maxlength="250" required="required"></td>
            </tr>
           <tr>
              <td>Description</td>
              <td><textarea rows="4" cols="45" name="description" maxlength="2000"></textarea></td>
           </tr>
            <tr>
               <td colspan="2"><input type = "submit" value = "Add"/></td>
            </tr>
        </table>
    </form>

    <section class="dhx_sample-container">
        <div class="dhx_sample-container__widget" id="grid"></div>
    </section>
    <script>
        var dataset = ${kpacs}
        var grid = new dhx.Grid("grid", {
          columns: [
            { width: 50, id: "id", header: [{ text: "ID" }],  },
            { width: 300, id: "title", header: [{ text: "Title" }] },
            { width: 600, id: "description", header: [{ text: "Description" }] },
            { width: 150, id: "creationDate", header: [{ text: "Creation Date" }] },
            { width: 50, id: "action", header: [{ text: "Delete" }],
                sortable: false,
                htmlEnable: true,
                align: "center",
                autoWidth: true,
                template: function (text, row, col) {
                     for(var d in dataset){
                         if(dataset[d]['id'] == row.id ){
                            return "<form action=\"kpacs/delete/" + row.id + "\" method=\"get\"><button style=\"margin-top: 5px\" type=\"submit\" name=\"id\"><i style=\"font-size:24px\" class=\"fa\">&#xf014;</i></button></form>"
                         }
                     }
                }
             }
          ],
          data: dataset
        });
    </script>
</body>
</html>
