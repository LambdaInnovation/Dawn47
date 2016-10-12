package cn.weaponry.core.ctrl;

import cn.weaponry.api.ctrl.KeyEventType;

public enum SyncEventType {
    UP(KeyEventType.UP),
    DOWN(KeyEventType.DOWN),
    ABORT(KeyEventType.ABORT),
    KEEPALIVE(KeyEventType.TICK);
    
    SyncEventType(KeyEventType ket) {
        keyEvent = ket;
    }
    
    public final KeyEventType keyEvent;
}
