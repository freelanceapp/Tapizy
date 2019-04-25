package ibt.com.tapizy.click_listener_interface;

import ibt.com.tapizy.model.conversation_modal.NewConversationQuestionsData;
import ibt.com.tapizy.model.conversation_modal.NewConversationSubResponseList;

public interface CustomClickListener {

    void getPosition(int parentPosition, int childPosition,
                     NewConversationQuestionsData questionsData, NewConversationSubResponseList subResponseData);
}
