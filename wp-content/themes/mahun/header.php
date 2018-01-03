<?php
  /**
   * The template for displaying the header
   **/

?><!DOCTYPE html>
<html <?php language_attributes(); ?> class="no-js" hreflang="en-us">
  <head>
  <meta name="google-site-verification" content="yvJk6V5mexkpQDw1vr4H0bE0uIMXrgpJSC9kPx0MDtw" />
  <meta charset="<?php bloginfo( 'charset' ); ?>">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Mason McIntyre</title>
  <meta name="description" content"Mason McIntyre is a software developer with over 9 years of programming experience. He specializes in web technologies both backend and frontend, due to his more recent experience. However, his knowledge is not limited to these technologies, as he started programming standalone desktop applications and had done so for several years before switching to web application development.">
  <link rel="stylesheet" type="text/css" media="screen" href="<?php echo get_template_directory_uri()?>/style.css">
  <?php wp_head(); ?>
</head>

<body <?php body_class(); ?> itemscope itemtype="http://schema.org/Person">
  <div class="container-fluid">
    <!-- Header Start -->
    <div id="header" class="row header">
      <div class="inner-width">
        <div class="logo-header pull-left">
          <a href="#"><img class="logo" alt="Logo Here" src="" /></a>
        </div>
        <a href="javascript:void(0);" class="hamburger"><i class="fa fa-bars"></i></a>
        <div class="primary-nav pull-right">
          <div class="phone-header-wrapper pull-right">
          </div>
          <ul>
            <li>
              <a id="about-nav" href="#about">About</a>
            </li>
            <li>
              <span>|</span>
            </li>
            <!--
            <li>
              <a id="projects-nav" href="#projets">Projects</a>
            </li>
            -->
          </ul>
        </div>
      </div>
    </div>
