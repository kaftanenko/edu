package com.diss.service.timesheet.management

import com.diss.service.timesheet.management.project.Project
import com.diss.service.timesheet.management.project.ProjectMember
import com.diss.service.timesheet.management.time.TimeFrame

class Activity {

	ActivityType type

	Project project

	ProjectMember projectMember

	TimeFrame timeFrame

	static constraints = {
	}
}
