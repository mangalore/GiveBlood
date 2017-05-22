package com.example.ananya.giveblood.service.handler;

public interface AlertDialogHandler {
    
    void onPositiveButtonClicked();
    
    void onNegativeButtonClicked();

    void onMultiChoiceClicked(int position, boolean isChecked);

}
