function getLanguage() {
  var filepath = location.pathname.substring(1);
  var res;
  
  if (filepath.indexOf("/it") !== -1) {
    res = "ITA";
  } else {
    res = "ENG";
  }
  
  return res;
}

function showLangSwitch() {

  var filepath = location.pathname.substring(1);
  var filename = filepath.substring(filepath.lastIndexOf("/")).substring(1);
  var path = filepath.substring(0,filepath.lastIndexOf("/"));
  var lang = getLanguage();
  var dest, img;

  if (lang == "ITA") {
    filename = filename.substring(3);
    path = "file:///".concat(path).concat("/");
    img = "images/".concat("ENG").concat(".png");
  } else {
    path = "file:///".concat(path.concat("/it-"));
    img = "images/".concat("ITA").concat(".png");
  }
  dest = path.concat(filename);

  document.write('<div id="lang"><a href="'.concat(dest).concat('"><img src="').concat(img).concat('" alt="').concat(lang).concat('" /></a></div>'));
}

function showTagLine() {
  var lang = getLanguage();
  
  if (lang == "ITA") {
    document.write('Un modo portabile di aggregare voti');
  } else {
    document.write('A portable way to aggregate votes');
  }
}

function showViewOnGitHub() {
  var lang = getLanguage();
  
  if (lang == "ITA") {
    document.write('Vedi progetto su GitHub');
  } else {
    document.write('View project on GitHub');
  }
}

function showToc() {
  
  var language = getLanguage();
  
  var toc = "Table of Contents";
  var index_url = "index.html", index_label = "Project Description";
  var dev_url = "dev_guidelines.html", dev_label = "Developer Guidelines";
  var mgmt_url = "project_management.html", mgmt_label = "Project Management";
  var req_url = "requirements.html", req_label = "Requirements";
  var bug_url = "bug_report.html", bug_label = "Bug Reporting";
  var downl_url = "downloads.html", downl_label = "Downloads";
  
  if (language == "ITA") {
    toc = "Indice";
    index_url = "it-".concat(index_url); index_label = "Descrizione del progetto";
    dev_url = "it-".concat(dev_url); dev_label = "Linee guida per gli sviluppatori";
    mgmt_url = "it-".concat(mgmt_url); mgmt_label = "Gestione del progetto";
    req_url = "it-".concat(req_url); req_label = "Requisiti";
    bug_url = "it-".concat(bug_url); bug_label = "Segnalazione dei bug";
    downl_url = "it-".concat(downl_url); downl_label = "Download";
  }
  
  document.write('\
		<h3>'+toc+'</h3>\
		<p>\
		<a href=\"'+index_url+'\">'+index_label+'</a><br />\
		<a href=\"'+dev_url+'\">'+dev_label+'</a><br />\
		<a href=\"'+mgmt_url+'\">'+mgmt_label+'</a><br />\
		<a href=\"'+req_url+'\">'+req_label+'</a><br />\
		<a href=\"'+bug_url+'\">'+bug_label+'</a><br />\
		<a href=\"'+downl_url+'\">'+downl_label+'</a><br />\
		</p>\
		');
}

function showLastMod() {

  var lang = getLanguage();
  var string = "Last update: ";
  var modDate = new Date(document.lastModified);
  var dateString = "ABCDE";
  
  var day = modDate.getDate();
  var mon = modDate.getMonth()+1;
  var yr = modDate.getFullYear();

  if (lang == "ITA") {
    string = "Ultimo aggiornamento: ";
    dateString = modDate.getDate()+"/"+(modDate.getMonth()+1)+"/"+modDate.getFullYear();
  } else {
    dateString = (modDate.getMonth()+1)+"/"+modDate.getDate()+"/"+modDate.getFullYear();
  }

  document.write('<p><em>'+string+dateString+'</em></p>');
}

function showOtherResources() {

  var lang = getLanguage();
  var title = "Other Resources";
  var prj = "GitHub project page";
  var auth = "GitHub author page";
  var preTitle = '\
        <h2>\
		  <a name=\"other-resources\" class=\"anchor\" href=\"#other-resources\">\
		    <span class=\"octicon octicon-link\"></span>\
		  </a>';
  var titleClose = '</h2>';
  var preProject = '<p><a href=\"http://dturrina.github.io/votesmanager\">';
  var projectClose = '</a><br />';
  var preAuthor = '<a href=\"https://github.com/dturrina\">';
  var authorClose = '</a></p>';
  
  if (lang == "ITA") {
    title = "Altre risorse";
	prj = "Pagina del progetto su GitHub";
	auth = "Pagina dell'autore su GitHub";
  }
  
  document.write(preTitle+title+titleClose);
  document.write(preProject+prj+projectClose+preAuthor+auth+authorClose);
}

function showOwner() {
  var lang = getLanguage();
  var maintains = " maintains ";
  
  if (lang == "ITA") {
    maintains = " gestisce ";
  }
  
  document.write('\
      <p>\
	    <a href=\"https://github.com/dturrina\" class=\"avatar\">\
		  <img src=\"https://2.gravatar.com/avatar/f60addb1590b2a5adfdba19d77af9aac?d=https%3A%2F%2Fidenticons.github.com%2Fbc826f2e636d044827df5902202f2593.png&amp;r=x&amp;s=60\" width=\"48\" height=\"48\"/>\
		</a>\
		<a href=\"https://github.com/dturrina\">dturrina</a>'+maintains+'<a href=\"https://github.com/dturrina/votesmanager\">VotesManager</a>\
	  </p>');
}