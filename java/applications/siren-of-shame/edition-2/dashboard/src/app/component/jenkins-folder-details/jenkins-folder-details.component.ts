import { Component, Input, OnInit } from '@angular/core';
import { JenkinsFolderDetails } from '../../model/jenkins-folder-details';

@Component({
  selector: 'app-jenkins-folder-details',
  templateUrl: './jenkins-folder-details.component.html',
  styleUrls: ['./jenkins-folder-details.component.css']
})
export class JenkinsFolderDetailsComponent implements OnInit {

  @Input()
  hierarchyLevel: number;

  @Input()
  folderDetails: JenkinsFolderDetails;

  constructor() { }

  ngOnInit() {
  }

  getGroupName() {

    if (this.folderDetails.displayName) {
      return "... » " + this.folderDetails.displayName;
    } else {
      return "... » " + this.folderDetails.name;
    }
  }

  getSubFolders() {
    return this.getJobNodesByClassRegEx("^.*\.Folder$");
  }

  getWorkflowJobs() {
    return this.getJobNodesByClassRegEx("^.*\.(MavenModuleSet|FreeStyleProject|WorkflowJob)$");
  }

  getJobNodesByClassRegEx(_classRegExPattern: string) {

    if (this.folderDetails.jobs) {

      return this.folderDetails.jobs
        .filter(job => job._class.match(_classRegExPattern));
    } else {
      return [];
    }
  }

  // ...

  getBootstrapColumnClasses() {

    if (this.getSubFolders().length <= 1) {
      return "col-lg-12 col-md-12 col-sm-12 col-xs-12";
    } else {
      switch (this.hierarchyLevel) {
        case 0:
        case 1:
          return "col-lg-6 col-md-6 col-sm-6 col-xs-12";
        case 2:
          return "col-lg-6 col-md-6 col-sm-12 col-xs-12";
        case 3:
          return "col-lg-6 col-md-12 col-sm-6 col-xs-12";
        default:
          return "col-lg-12 col-md-12 col-sm-12 col-xs-12";
      }
    }
  }

}
