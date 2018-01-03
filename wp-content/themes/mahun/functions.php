<?php

  /* Add bootstrap support to the Wordpress theme*/

function theme_add_js() {
  wp_enqueue_script('jquery', 'https://ajax.googleapis.com/ajax/libs/jquery/2.2.2/jquery.min.js', '', '2.2.2', true);
  wp_enqueue_script( 'bootstrap-js', get_template_directory_uri() . '/js/bootstrap.min.js', '', '3.0.0', true );
}

function theme_add_bootstrap() {
  wp_enqueue_style( 'bootstrap-css', get_template_directory_uri() . '/css/bootstrap.min.css' );
}

add_action( 'wp_enqueue_scripts', 'theme_add_js' );
add_action( 'wp_enqueue_scripts', 'theme_add_bootstrap' );

// Move jQuery to the footer
wp_scripts()->add_data( 'jquery', 'group', 1 );
wp_scripts()->add_data( 'jquery-core', 'group', 1 );
wp_scripts()->add_data( 'jquery-migrate', 'group', 1 );
