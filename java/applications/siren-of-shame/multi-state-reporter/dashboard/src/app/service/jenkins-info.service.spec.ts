import { TestBed, inject } from '@angular/core/testing';

import { JenkinsInfoService } from './jenkins-info.service';

describe('JenkinsInfoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [JenkinsInfoService]
    });
  });

  it('should be created', inject([JenkinsInfoService], (service: JenkinsInfoService) => {
    expect(service).toBeTruthy();
  }));
});
