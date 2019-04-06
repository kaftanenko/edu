import { JobDetails } from './jenkins-job-details';

export class JenkinsFolderDetails {

    _class: string;
    actions?: any;
    views?: any;
    primaryView?: any;

    name: string;
    fullName?: string;

    displayName?: string;
    fullDisplayName?: string;
    displayNameOrNull?: string;

    url: string;
    description?: string;

    healthReport?: any;

    jobs?: JenkinsFolderDetails[] = [];

}
