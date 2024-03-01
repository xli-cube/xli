package com.xli.dto.validation.group;

import jakarta.validation.GroupSequence;

@GroupSequence({IGroup.groupA.class, IGroup.groupB.class, IGroup.groupC.class, IGroup.groupD.class})
public interface IGroup {

    interface search {
    }

    interface add {
    }

    interface delete {
    }

    interface update {
    }

    interface detail {
    }

    interface groupA {
    }

    interface groupB {
    }

    interface groupC {
    }

    interface groupD {
    }
}
