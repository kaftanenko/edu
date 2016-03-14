package edu.java.jse.validation.vehicle.model.pkw.validation.annotation.group;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({ Default.class, PartsValidationGroup.class })
public interface AllGroups {

}
