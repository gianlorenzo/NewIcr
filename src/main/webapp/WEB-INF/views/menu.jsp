<%@ page import="it.uniroma3.icr.instagramConfig.Constants" %>
<!-- Page Wrapper -->
	<div id="page-wrapper">
		<!-- Header -->
		<header id="header" class="alt">
			
			<nav id="nav">
				<ul>
					<li class="special"><a href="#menu" class="menuToggle"><span>Menu</span></a>
						<div id="menu">
							<ul>
								<li><a href="login">Log In</a></li>
							</ul>
							<ul>
								<li>
									<a href="https://www.instagram.com/oauth/authorize/?client_id=07dc21f8d0ed4f36ae1f8ded4283db1a&redirect_uri=http://localhost:8080/connect/instagramConnected&response_type=code">
										Oauth Instagram
									</a>
								</li>
							</ul>
						</div></li>
				</ul>
			</nav>
		</header>
	</div>