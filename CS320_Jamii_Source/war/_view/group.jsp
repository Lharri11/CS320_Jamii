<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html><head>
	<link rel="icon" type="image/png" href="images/favicon.png">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="generator" content="Script Eden ( http://scripteden.net/ ) Template Builder v2.0.0">  
    <title>groupName -  Jamii</title>
    <!--pageMeta-->

    <!-- Loading Bootstrap -->
    <link href="bootstrap/css/bootstrap.css" rel="stylesheet">

    <!-- Loading Flat UI -->
    <link href="css/flat-ui.css" rel="stylesheet">
    
    <link href="css/style.css" rel="stylesheet">

    

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
    
    <!--headerIncludes-->
     
    
</head>
<body>
    
    <div id="page" class="page">
        
    <header class="item header margin-top-0 header10" id="header10">
    
    		<div class="wrapper">
    	
    			<nav role="navigation" class="navbar navbar-purple navbar-embossed navbar-lg navbar-fixed-top">
    					
    				<div class="container">
    				
    					<div class="navbar-header">
    						<button data-target="#navbar-collapse-02" data-toggle="collapse" class="navbar-toggle" type="button">
    							<span class="sr-only">Toggle navigation</span>
    						</button>
    						<a href="home" class="navbar-brand brand"><img src="images/TransparentWhite.png" id="logo"></a>
   						</div>
    					<!--/.navbar-header -->
    					<!-- HERE IS YOUR SEARCH JSP THING -->
    					<form class="search-collapse" action="${pageContext.servletContext.contextPath}/searchSite" method="post" >
                            <input type="text" id="searchText"placeholder="Search..." required>
                            <input type="image" src="images/search.png" value="Search" id="searchButton">
						</form><br>
	                    
    					<div id="navbar-collapse-02" class="collapse navbar-collapse">
    						<ul class="nav navbar-nav navbar-right">
    							<li class="propClone"><a href="http://localhost:8081/Jamii/user"><img src="images/user.png" class="tabIcon"></a></li>
    							<li class="propClone"><a href="#"><img src="images/gear.png" class="tabIcon"></a></li>
    							<li class="propClone"><a href="#"><img src="images/logout.png" class="tabIcon"></a></li>
    						</ul>
    					</div> 
    					<!--/.navbar-collapse -->
    				</div><!-- /.container -->
    			</nav>
    			
    		<!--pageContent-->
    			<div class="pageContent">
    				<div id="coverPhotoContainer">
    				<!-- HERE IS WHERE THE COVER PHOTO JSP THING IS : "${group.img}"-->
    					<img src="images/coverTest.jpg" id="coverPhoto">
    				</div>
    				<div class="pageInfo">
    					<div id="rating"> :) </div>
    					<c:forEach items="${groups}" var="group">
			        		<p id="pageName">${group.name}</p>	
			    		</c:forEach>
    					<div id="pageLinks"> 
    						<a href="#">${group.memberCount} Members</a> |
    						<a href="#">Photos</a> |
    						<a href="#">Rules</a> |
    						<a href="#">Leave </a>
    					</div>
    				</div>
    				
    				<div class="sidebar">
    					<button  onclick="toggleSideBar()" id="toggle" style="right: 0px;" >Group Options</button>
    					<div id="sideBar" style="right: -300px;">
    						<ul class="list-unstyled groupList">
  								<li class="groupListItem"><a href="http://localhost:8081/Jamii/group" style="color: white">Enter Main Group</a></li>
 							 	<li class="groupListItem">Create New Group</li>
  								<li class="groupListItem">Group option</li>
							</ul>
    					</div>
    				</div>
    				
    				<div class="postForm">
    					<img src="images/userTest.jpg" class="postPhotos">
    					<form id="post" action="${pageContext.servletContext.contextPath}/newPost" method="post">
    						<textarea rows="4" cols="50" id="newPost" name="newPost" placeholder="What do you want this group to know?" value="${postText}" form="post" required></textarea>
                			<input type="Submit" value="Post" id="postSubmit" name="postSubmit">
    					</form>  
    				</div>
    				
    				<div class="aPost">
    					<img src="images/userTest.jpg" class="postPhotos">
    					<a class="name" href="#">Alyssa McDevitt</a>
    					<h6 class="date">April 29, 2017, 3:23 PM</h6>
    					<h4 class="postText">Test</h4>
 					</div>
    			</div>
    			
    			
    			
    		<!--/pageContent-->
    		
    		</div><!-- /.wrapper -->
    	
    	</header><!-- /.item --></div><!-- /#page -->
    <!-- Load JS here for greater good =============================-->
    <script src="js/jquery-1.8.3.min.js"></script>
    <script src="js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="js/jquery.ui.touch-punch.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-select.js"></script>
    <script src="js/bootstrap-switch.js"></script>
    <script src="js/flatui-checkbox.js"></script>
    <script src="js/flatui-radio.js"></script>
    <script src="js/jquery.tagsinput.js"></script>
    <script src="js/jquery.placeholder.js"></script>
	<script src="js/jquery.nivo.slider.pack.js"></script>
    <script src="js/application.js"></script>
	<script src="js/over.js"></script>
	<script>
	
	function toggleSideBar() {
    	var x = document.getElementById('sideBar');
    	var y = document.getElementById('toggle');
    	if (x.style.right === '-300px') {
        	x.style.right = '0%';
        	y.style.right = '300px';
    	} else {
        	x.style.right = '-300px';
        	y.style.right = '0px';
    	}
	}
	
	$(function(){
		
		if( $('#nivoSlider').size() > 0 ) {
		
	    	$('#nivoSlider').nivoSlider({
	    		effect: 'random',
				pauseTime: 5000
	    	});
		
		}
		
	})
	</script>


</body></html>