<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head id="ctl00_ctl00_NICEMasterPageHead">
<meta name="viewport"
	content="width=device-width, target-densityDpi=160dpi, initial-scale=1" />
<meta name="MobileOptimized" content="width" />
<meta name="HandheldFriendly" content="true" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta http-equiv="cleartype" content="on" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<link
	href="https://framework.web.cern.ch/framework/2.0/fonts/PTMonoWeb/PTM55Fstylesheet.css"
	rel="Stylesheet" type="text/css" />
<link
	href="https://framework.web.cern.ch/framework/2.0/fonts/PTSansWeb/PTSansWeb.css"
	rel="Stylesheet" type="text/css" />
<link
	href="https://framework.web.cern.ch/framework/2.0/fonts/PTSerifWeb/PTSerifWeb.css"
	rel="Stylesheet" type="text/css" />
<link
	href="https://login.cern.ch/adfs/ls/MasterPages/toolbar/css/toolbar.css"
	rel="stylesheet" type="text/css" />

<!--[if lt IE 8]>
        <link href="/adfs/ls/MasterPages/toolbar/css/toolbar-ie.css" rel="stylesheet" type="text/css" />
    <![endif]-->

<title>Cern Authentication</title>

<link href="https://login.cern.ch/adfs/ls/favicon.ico" rel="ICON"
	type="image/x-icon" />
<link href="https://login.cern.ch/adfs/ls/favicon.ico"
	rel="SHORTCUT ICON" type="image/x-icon" />
<meta name="application-name" content="sso-management" />

<!-- up to 5 tasks can be defined -->
<link
	href="https://login.cern.ch/adfs/ls/App_Themes/Default/01-CernWeb-General.css"
	type="text/css" rel="stylesheet" />
<link
	href="https://login.cern.ch/adfs/ls/App_Themes/Default/02-CernWeb-Header.css"
	type="text/css" rel="stylesheet" />
<link
	href="https://login.cern.ch/adfs/ls/App_Themes/Default/03-CernWeb-OISStyle.css"
	type="text/css" rel="stylesheet" />
<link
	href="https://login.cern.ch/adfs/ls/App_Themes/Default/06-CustomStyle.css"
	type="text/css" rel="stylesheet" />
<link
	href="https://login.cern.ch/adfs/ls/App_Themes/Default/10-CernWeb-SmallDevices.css"
	type="text/css" rel="stylesheet" />
<title>Error</title>
</head>

<body id="ctl00_ctl00_NICEMasterPageBody">

	<form name="aspnetForm" method="post"
		action="https://login.cern.ch/adfs/ls/adfs/ls/?wa=wsignout1.0"
		id="aspnetForm">

		<div>
			<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE"
				value="/wEPDwUKMjE0NDEyNDc2NWRk" />
		</div>
		<div>
			<input type="hidden" name="__EVENTVALIDATION" id="__EVENTVALIDATION"
				value="/wEWAgKQpcW+DgLnmcnFAQ==" />
		</div>
		<input type="hidden" name="__db" value="22" />


		<div id="ctl00_ctl00_pnlToolbar">
			<div id="cern-toolbar">
				<h1>
					<a href="http://cern.ch" title="CERN">CERN <span>Accelerating
							science</span></a>
				</h1>
				<ul class="cern-signedin">
					<li class="cern-accountlinks active"><a class="cern-account"
						title="Sign in to your CERN account">Sign in</a></li>
					<li><a class="cern-directory" href="http://cern.ch/directory"
						title="Search CERN resources and browse the directory">Directory</a></li>
				</ul>
			</div>
		</div>

		<div id="ctl00_ctl00_pnlHeader">
			<div id="header">
				<div id="ctl00_ctl00_HeaderTitle" class="bgHeaderImage page">
					<h2 id="site-name">
						<a href="/" title="Home" rel="home">CERN Single Sign-On</a>
					</h2>
					<h3 id="site-slogan">Sign in with a CERN account, a Federation
						account or a public service account</h3>
				</div>

				<div id="main-navigation" class="page">&nbsp;</div>
			</div>
		</div>

		<div id="page">
			<table style="margin-top: 20px; margin-bottom: 20px; width: 100%">
				<tr>
					<td style="width: 51px; padding-bottom: 10px;"><img
						src="https://login.cern.ch/adfs/ls/images/error.gif" border="0"
						alt="error" /></td>
					<td style="padding-left: 10px; padding-bottom: 10px;"><h2>
							<span
								id="ctl00_ctl00_NICEMasterPageBodyContent_SiteContentPlaceholder_lblErrorTitle">
								A problem occurred during the sign out process. Please close the
								browser to complete sign out, or follow the steps described in
								the Knowledge Base article: </span>
						</h2>
						<h2>
							<span
								id="ctl00_ctl00_NICEMasterPageBodyContent_SiteContentPlaceholder_lblErrorTitle"><i>Un
									problème est survenu pendant le processus de déconnexion. Afin
									d'assurer une déconnexion complète, veuillez s'il vous plaît
									fermer votre navigateur, ou suivez les étapes décrites dans
									l'article de la Base de connaissances: </i></span>
						</h2></td>
				</tr>
				<tr>
					<td colspan="2"
						style="padding: 10px 10px 10px 10px; border: solid 1px #dddddd;"><span
						id="ctl00_ctl00_NICEMasterPageBodyContent_SiteContentPlaceholder_ExceptionMessageLabel">
							<strong>KB0002255 </strong>
					</span> <a
						href="https://cern.service-now.com/service-portal/article.do?n=KB0002255">How
							to clear cache and cookies on the 3 main Internet Browsers?</a></td>
				</tr>
			</table>
		</div>

		<div id="ctl00_ctl00_pnlFooter">
			<div id="footer">
				<div class="page">
					<div class="region-footer-first">
						<h2>Related sites</h2>
						<ul>
							<li class="leaf"><a
								href="https://account.cern.ch/account/Help/?kbid=053010"
								target="_blank">Need password help ?</a></li>
							<li class="leaf"><a href="http://cern.ch/account"
								target="_blank">Create/Check your account</a></li>
							<li class="leaf"><a href="http://www.cern.ch/service-portal"
								target="_blank">Service Desk</a> <span class='subtle'>+41
									22 76 77777</span></li>
							<li class="leaf"><a
								href="http://cern.ch/it-support-servicestatus/" target="_blank">IT
									Services Status Board</a></li>
							<li class="leaf"><a href="http://www.cern.ch/service-portal"
								target="_blank">Service Portal (Get Help)</a></li>
						</ul>
					</div>
					<div class="cern-logo">
						<a href="http://cern.ch" title="CERN" rel="CERN"><img
							class="footer_logo_img" border="0"
							src="https://login.cern.ch/adfs/ls/Images/cern-logo-large.png"
							alt="CERN" style="border-width: 0px;" /></a>
					</div>
				</div>
			</div>
		</div>

	</form>

</body>
</html>
