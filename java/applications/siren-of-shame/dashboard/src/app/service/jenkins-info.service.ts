import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class JenkinsInfoService {

  config: {
    baseURL: ""
  };

  constructor(private http: HttpClient) {

    this.config = {
      baseURL: ""
    };
  }

  getSummaryInfo(resourcePath: string) {

    let fullResourcePath = this.config.baseURL + resourcePath;
    return this.http.get(fullResourcePath, {});
  }

}
