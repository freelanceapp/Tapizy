package infobite.ibt.tapizy.click_listener_interface;

import infobite.ibt.tapizy.model.conversation_modal.NewConversationQuestionsData;
import infobite.ibt.tapizy.model.conversation_modal.NewConversationSubResponseList;

public interface CustomClickListener {

    void getPosition(int parentPosition, int childPosition,
                     NewConversationQuestionsData questionsData, NewConversationSubResponseList subResponseData);
}
