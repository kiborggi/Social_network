<%@ page import="clases.Message" %>
<%@ page import="clases.User" %>
<%@ page import="clases.MessageDAO" %>
<%@ page import="java.util.TreeSet" %>
<%@ page import="com.sun.source.tree.Tree" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="clases.JDBSConnector" %><%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 29.10.2020
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%
    JDBSConnector userDAO = new JDBSConnector();
    userDAO.createConnect();
    MessageDAO messageDAO = new MessageDAO();
    messageDAO.createConnect();
    User user = (User) session.getAttribute("user");
    String messagers  = "";
    String messages = "";
    TreeSet<Integer> messagersId = null;
    try {
         messagersId = messageDAO.getUniqMessageers(user.getId());
    } catch (SQLException e) {
        e.printStackTrace();
    }
    for(Integer id : messagersId) {
        try {
            messagers += "  <li class =\"dialog\">\n" +
                    "                        <div >\n" +
                    "                            <a href=\"/Social_network_war_exploded/getMessage?with=" +
                    id +
                    "\">" +
                    userDAO.getByID(id).getName() +
                    "</a>\n" +
                    "                            <br>\n" +
                    "                            <h3> " +
                    messageDAO.getLastMessage(user.getId(),id) +
                    "</h3>\n" +
                    "                        </div>\n" +
                    "                    </li> ";

        } catch (NullPointerException e){
            System.out.println("null pointer");

        }
    }

   if(  session.getAttribute("messagingWith") != null ){
       int idwith = Integer.parseInt((String) session.getAttribute("messagingWith"));
       for (Message mes : messageDAO.getCorrespondence(user.getId(), idwith)){

           messages += "<li class =\"message\">\n" +
                   "          <div >\n" +
                   "            <h2>" +
                         mes.getDate() +" "  +   userDAO.getByID(mes.getFromid()).getName() +
                   " : </h2>\n" +
                   "            \n" +
                   "            <p>" +
                   mes.getMessage() +
                   "</p>\n" +
                   "          </div>\n" +
                   "        </li>";

       }

   }


%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html>
<html lang='ru'>
<head>
    <style>
        html, body {
            margin:0;
            padding:0;
            font-family: Roboto;
        }
        body{
            background: white;
            color:black;
        ;

        }

        .content{
            display: -webkit-box;
            display: flex;
            -webkit-box-align: start;
            align-items: center;
            position: relative;
            min-height: 100%;
            -moz-justify-content: space-between;
            -ms-justify-content: space-between;
            -webkit-box-pack: justify;
            justify-content: space-between;
            color : black;
        }

        .messegers{
            width: 30%;
            border: 1px;
            padding: 5px;
            padding:50px;

        }

        .messeges{
            width: 60%;
            border: 1px;
            padding: 5px;
            padding:50px;

        }

        .scrollbar
        {
            height :800px;
            margin-right: 5px;
            margin-left: 5px;
            width: 90%;
            float: left;
            overflow-y: scroll;


        }
        #style-1::-webkit-scrollbar-track
        {
            -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
            border-radius: 10px;
            background-color: #b7dfb9;
        }

        #style-1::-webkit-scrollbar
        {
            width: 12px;
            background-color: #b7dfb9;
        }

        #style-1::-webkit-scrollbar-thumb
        {
            border-radius: 10px;
            -webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
            background-color: #555;
        }

        .dialog{
            width: 90%;
            background-color:#449d48;
            margin : 5px;
            list-style-type: none;
        }
    </style>

    <title>index</title>


</head>

<body>
<div class = "content">
    <div class ="messegers" >
        <form action="sendMessage">
            <input type ="number" name ="fromId" >
            <button type="submit"> initialization </button>
        </form>

        <div class="scrollbar" id="style-1">
            <div class="force-overflow">

                <ul>
                    <%= messagers %>
                </ul>
            </div>
        </div>
    </div>


    <div class ="messeges" >
        <div class="scrollbar" id="style-1">
            <div class="force-overflow">
                <ul>
                    <%= messages %>
                </ul>

            </div>
        </div>
        <form action="sendMessage">
        <input type="text" name="message">
        <button type="submit"> send </button>
        </form>

    </div>

</div>





</body>
</html>
