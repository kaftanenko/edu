import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { JenkinsFolderDetailsComponent } from './component/jenkins-folder-details/jenkins-folder-details.component';
import { JobDetailsComponent } from './component/jenkins-job-details/jenkins-job-details.component';
import { JenkinsInfoService } from './service/jenkins-info.service';


@NgModule({
  declarations: [
    AppComponent,
    JenkinsFolderDetailsComponent,
    JobDetailsComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    JenkinsInfoService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
