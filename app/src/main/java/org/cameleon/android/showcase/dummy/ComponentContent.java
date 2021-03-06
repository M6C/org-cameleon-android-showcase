package org.cameleon.android.showcase.dummy;

import org.cameleon.android.showcase.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentContent {

    public static final List<ComponentItem> ITEMS = new ArrayList<ComponentItem>();

    static {
        ITEMS.add(new ComponentItem(0, R.layout.item_detail_text, R.string.component_item_text, R.string.component_item_text_description));
        ITEMS.add(new ComponentItem(1, R.layout.item_detail_button, R.string.component_item_button, R.string.component_item_button_description));
        ITEMS.add(new ComponentItem(2, R.layout.item_detail_widget, R.string.component_item_widget, R.string.component_item_widget_description));
        ITEMS.add(new ComponentItem(3, R.layout.item_detail_dialog, R.string.component_item_dialog, R.string.component_item_dialog_description));
        ITEMS.add(new ComponentItem(4, R.layout.item_detail_menu, R.string.component_item_menu, R.string.component_item_menu_description));
        ITEMS.add(new ComponentItem(5, R.layout.item_detail_bottom_sheet, R.string.component_item_bottom_sheet, R.string.component_item_bottom_sheet_description));
    }

    public static class ComponentItem implements Serializable {
        public final int idItem;
        public final int idDetail;
        public final int idItemText;
        public final int idItemDesc;

        public ComponentItem(int idItem, int idDetail, int idItemText, int idItemDesc) {
            this.idItem = idItem;
            this.idDetail = idDetail;
            this.idItemText = idItemText;
            this.idItemDesc = idItemDesc;
        }
    }
}
