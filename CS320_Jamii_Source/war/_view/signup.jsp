<!DOCTYPE html>
<html>
<head>
	<link rel="icon" type="image/png" href="images/favicon.png">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="generator" content="Script Eden ( http://scripteden.net/ ) Template Builder v2.0.0">  
    <!--pageMeta-->
	<title>Join Jamii</title>
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
    						<a href="home" class="navbar-brand brand"><img src="images/TransparentWhite.png" id="logo"></a>
   						</div>
    					
    					
    				</div><!-- /.container -->
    			</nav>
    	
    		<form id="login" class="login" action="${pageContext.servletContext.contextPath}/login" method="post">
                <input type="text" id="username" name="username" placeholder="Username" value="${username}" required>
                <input type="password" id="password" name="password" placeholder="Password" value="${password}" required>
                <input type="text" id="name" name="name" placeholder="Name" value="${name}" required>
                <input type="text" id="email" name="email" placeholder="Email" value="${email}" required>
                <input type="text" id="phone" name="phone" placeholder="Phone Number" value="${email}" required>
                <input type="Submit" value="Sign Up" id="loginSubmit" name="signupSubmit">
                
			</form><!-- /.login -->	
			
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