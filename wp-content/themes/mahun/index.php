<?php

get_header(); ?>
<div id="about" class="row about">
  <div class="inner-width">
    <div id="name" class="name col-xs-12">
      <h1 class="text-left col-xs-12"><span itemprop="name"><span itemprop="givenName">Mason</span> <span itemprop="familyName">McIntyre</span></span></h1>
    </div>
    <div id="objective" class="objective col-xs-12">
      <h3 class="col-xs-12 col-md-4">Objective</h3>
      <div class="col-xs-12 col-md-8">
        <p>Seeking a challenging, full time position in software development that will allow me to utilize my creativity to make an 
impact on technical projects.</p>
      </div>
    </div>
    <div id="experience" class="experience col-xs-12">
      <h3 class="col-xs-12 col-md-4">Experience</h3>
        <div class="col-xs-12 col-md-8">
          <div>
            <h4><span class="job-title" itemprop="jobTitle">Web Developer</span>, <span itemprop="worksFor"><a target="_blank" href="http://www.liquidint.com">Liquid Interactive</a></span></h4>
            <h5>November 2015 - Present</h5>
            <p>Performed work on redesign and maintenance projects for dynamic and static webpages using HTML, CSS, Less, PHP, C#, 
JavaScript, Angular JS, Node.js, Gulp, as well as CMS’s like Wordpress, Umbraco, and Sitecore. Created custom C# data models 
used to render Umbraco partial views, aiding in speed and organization in the production of Umbraco websites. Used IIS and 
Apache to configure development and staging server environments, as well as update code on live servers. Load balanced internal 
servers used for client’s development environment. Used Vagrant alongside Scotch Box and VVV to manage and configure local 
development environments. Worked with Microsoft SQL Server, MySQL, and HeidiSQL to manipulate database data.</p>
           </div>
           <div>
             <h4>Software Developer, <a target="_blank" href="http://www.baseballinfosolutions.com">BaseballInfoSolutions</a> 
/ <a target="_blank" href="http://www.sportsinfosolutions.com">SportsInfoSolutions</a></h4>
             <h5>June 2015 - November 2015</h5>
             <p>Developed dynamic and static web form pages in ASP.NET. Created underlying database architecture for aggregating 
football data. Created intuitive GUI’s to manipulate data in databases. Developed front end component of MLB lineup optimizer, 
passing arguments to server-side Ruby programs, and parsing output to an interactive user interface.</p>
           </div>
           <div>
             <h4>Software Development Intern, <a target="_blank" href="http://www.trailkam.com">TrailKam.com</a></h4>
             <h5>January 2015 - June 2015</h5>
             <p>Performed work closely with the founder of TrailKam, Bernie Graham, to get the project to a minimal viable 
product (MVP).  Helped with software design and testing. Presented work at the Eastern Colleges Science Conference in Niagara, 
NY.</p>
	   </div>
           <div>
             <h4>Software Development Intern, <a target="_blank" href="http://www.brainrush.com">BrainRush.com</a></h4>
             <h5>May 2014 - November 2014</h5>
             <p>As an intern my main responsibilities included, testing, software development and estimation. Project was written 
in PHP Symfony 2.</p>
           </div>
           <div>
             <h4>Technology Support Technician, Easton Area High School</h4>
             <h5>May 2014 - September 2014</h5>
             <p>Computer maintenance; including administration, imaging, troubleshooting, and repairs.</p>
	    </div>
          </div>
	</div>
	<div id="education" class="education col-xs-12">
          <h3 class="col-xs-12 col-md-4">Education</h3>
          <div class="col-xs-12 col-md-8">
	    <p>B.S, Computer Science - <span itemprop="alumniOf"><a target="_blank" href="http://www.wilkes.edu/academics/colleges/science-and-engineering/mathematics-computer-science/computer-science/index.aspx">Wilkes 
University</a></span> - 2015</p>
	  </div>
	</div>
	<div id="skills" class="skills col-xs-12">
          <h3 class="col-xs-12 col-md-4">Skills</h3>
          <div class="col-xs-12 col-md-8">
	    <ul>
	      <li>Wrote first program on Apple \\e in eighth grade</li>
	      <li itemprop="award">All-American Wrestler</li>
	      <li itemprop="award">2011 PIAA AAA Wrestling Team State Runner-up, Varsity Wrestler</li>
	      <li itemprop="award">2011 PIAA AAAA District Champion Football Team, First Team Varsity Player</li>
	    </ul>
	  </div>
	</div>
      </div>
    </div>
    <div id="projects" class="row projects">
<!--
      <a href="<?php //echo get_template_directory_uri()?>/website-projects/asteroid-belt-assult/Asteroid Belt Assault.exe">Click</a>
-->
<?php
/*
  if ($handle = opendir('./wp-content/themes/mahun/projects/checkers/')) {
    while (false !== ($file = readdir($handle))) {
      if ($file != "." && $file != "..") {
        $thelist .= '<li><a href="'.$file.'">'.$file.'</a></li>';
      }
    }
  closedir($handle);
  }
  
  int $file = readfile('./wp-content/themes/mahun/projects/checkers/checkers.tar.gz'), true);
*/
?>
    </div>
  </div> 
</div>

<?php get_footer();
