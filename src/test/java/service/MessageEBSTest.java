package service;

import Message.abstractions.BinaryMessage;
import Message.toSMEV.MessageSMEV;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageEBSTest {

    @Test
    public void restoreMessageEBS() {
        MessageSMEV mebs = new MessageSMEV();
        mebs.DataToWork = "test".getBytes();
        mebs.ID="0000";
        mebs.pseudo="ebs";
        MessageSMEV restored = (MessageSMEV) BinaryMessage.restored(BinaryMessage.savedToBLOB(mebs));
        assertEquals(mebs.ID, restored.ID);
        assertEquals(mebs.pseudo, restored.pseudo);
        assertEquals(new String(mebs.DataToWork), new String(restored.DataToWork));
    }
}