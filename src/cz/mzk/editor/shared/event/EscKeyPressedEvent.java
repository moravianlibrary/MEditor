/*
 * Metadata Editor
 * @author Jiri Kremser
 * 
 * 
 * 
 * Metadata Editor - Rich internet application for editing metadata.
 * Copyright (C) 2011  Jiri Kremser (kremser@mzk.cz)
 * Moravian Library in Brno
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 * 
 */

package cz.mzk.editor.shared.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.smartgwt.client.event.Cancellable;

public class EscKeyPressedEvent
        extends GwtEvent<EscKeyPressedEvent.EscKeyPressedHandler>
        implements Cancellable {

    private boolean cancel = false;

    public interface HasEscKeyPressedHandlers
            extends HasHandlers {

        HandlerRegistration addEscKeyPressedHandler(EscKeyPressedHandler handler);

    }

    public interface EscKeyPressedHandler
            extends EventHandler {

        public void onEscKeyPressed(EscKeyPressedEvent event);
    }

    private static final Type<EscKeyPressedHandler> TYPE = new Type<EscKeyPressedHandler>();

    public static void fire(HasHandlers source) {
        source.fireEvent(new EscKeyPressedEvent());
    }

    public static Type<EscKeyPressedHandler> getType() {
        return TYPE;
    }

    public EscKeyPressedEvent() {
    }

    @Override
    public Type<EscKeyPressedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EscKeyPressedHandler handler) {
        handler.onEscKeyPressed(this);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "EscKeyPressedEvent[" + "]";
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void cancel() {
        cancel = true;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean isCancelled() {
        return cancel;
    }

}
