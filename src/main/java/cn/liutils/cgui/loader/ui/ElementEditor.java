/**
 * Copyright (c) Lambda Innovation, 2013-2015
 * 本作品版权由Lambda Innovation所有。
 * http://www.li-dev.cn/
 *
 * This project is open-source, and it is distributed under  
 * the terms of GNU General Public License. You can modify
 * and distribute freely as long as you follow the license.
 * 本项目是一个开源项目，且遵循GNU通用公共授权协议。
 * 在遵照该协议的情况下，您可以自由传播和修改。
 * http://www.gnu.org/licenses/gpl.html
 */
package cn.liutils.cgui.loader.ui;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cn.liutils.cgui.gui.Widget;
import cn.liutils.cgui.gui.component.Component;
import cn.liutils.cgui.gui.component.TextBox;
import cn.liutils.cgui.gui.component.Tint;
import cn.liutils.cgui.gui.event.ChangeContentEvent;
import cn.liutils.cgui.gui.event.ChangeContentEvent.ChangeContentHandler;
import cn.liutils.cgui.gui.event.ConfirmInputEvent;
import cn.liutils.cgui.gui.event.ConfirmInputEvent.ConfirmInputHandler;
import cn.liutils.cgui.gui.event.FrameEvent;
import cn.liutils.cgui.gui.event.FrameEvent.FrameEventHandler;
import cn.liutils.cgui.gui.event.LostFocusEvent;
import cn.liutils.cgui.gui.event.LostFocusEvent.LostFocusHandler;
import cn.liutils.cgui.gui.event.MouseDownEvent;
import cn.liutils.cgui.gui.event.MouseDownEvent.MouseDownHandler;
import cn.liutils.util.client.HudUtils;
import cn.liutils.util.client.RenderUtils;
import cn.liutils.util.helper.Color;
import cn.liutils.util.helper.Font;
import cn.liutils.util.helper.GameTimer;
import cn.liutils.util.helper.TypeHelper;

/**
 * @author WeAthFolD
 *
 */
public abstract class ElementEditor extends Widget {
    
    ComponentEditor editor;
    final Field targetField;
    
    public ElementEditor(Field f) {
        targetField = f;
    }
    
    boolean tryEdit(String value) {
        return TypeHelper.edit(targetField, getEditInstance(), value);
    }
    
    Object getEditInstance() {
        return getTargetComponent();
    }
    
    ComponentEditor getEditor() {
        return editor;
    }
    
    Component getTargetComponent() {
        return getEditor().target;
    }
    
    void updateTargetWidget() {
        getEditor().widget.dirty = true;
    }
    
    @Override
    public void onAdded() {
        updateValue();
        
        regEventHandler(new FrameEventHandler() {
            int slowdown = 0;
            
            @Override
            public void handleEvent(Widget w, FrameEvent event) {
                if(++slowdown >= 30) {
                    slowdown = 0;
                    if(canUpdateValue()) {
                        updateValue();
                    }
                }
            }
        });
    }
    
    public abstract void updateValue();
    
    public abstract boolean canUpdateValue();
    
    /**
     * Default element editor. This is a special one and has hardcoded delegation.
     */
    public static final class Default extends ElementEditor { 
        
        public Default(Field f) {
            super(f);
        }

        @Override
        public void updateValue() {}

        @Override
        public boolean canUpdateValue() {
            return false;
        }
        
    }
    
    public static final class CheckBox extends ElementEditor {
        
        static final ResourceLocation
            OUTLINE = GuiEdit.tex("check_back"),
            CHECK = GuiEdit.tex("check");
        
        boolean state;
        
        public CheckBox(Field f) {
            super(f);
            
            transform.x = 80;
            transform.y = -10;
            transform.setSize(0, 0);
        }
        
        @Override
        public void onAdded() {
            super.onAdded();
            
            regEventHandler(new MouseDownHandler() {
                @Override
                public void handleEvent(Widget w, MouseDownEvent event) {
                    state = !state;
                    
                    TypeHelper.set(targetField, getEditInstance(), state);
                }
            });
            
            regEventHandler(new FrameEventHandler() {
                boolean firstLoad = true;
                
                @Override
                public void handleEvent(Widget w, FrameEvent event) {
                    if(firstLoad) {
                        firstLoad = false;
                        transform.setSize(10, 10);
                        w.dirty = true;
                    }
                    GL11.glColor4d(1, 1, 1, .8);
                    RenderUtils.loadTexture(OUTLINE);
                    HudUtils.rect(0, 0, 10, 10);
                    
                    if(state) {
                        RenderUtils.loadTexture(CHECK);
                        HudUtils.rect(0, 0, w.transform.width, w.transform.height);
                    }
                }
            });
        }

        @Override
        public void updateValue() {
            state = (Boolean) TypeHelper.get(targetField, getEditInstance());
        }

        @Override
        public boolean canUpdateValue() {
            return true;
        }
        
    }
    
    public static class ColorBox extends ElementEditor {

        public ColorBox(Field f) {
            super(f);
            transform.setSize(10, 12);
        }
        
        @Override
        public Object getEditInstance() {
            try {
                return targetField.get(super.getEditInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        
        @Override
        public void onAdded() {
            String[] arr = new String[] { "r", "g", "b", "a" };
            double x = 5;
            for(final String s : arr) {
                Widget drawer = new Widget();
                drawer.regEventHandler(new FrameEventHandler() {
                    @Override
                    public void handleEvent(Widget w, FrameEvent event) {
                        Font.font.draw(s, 0, 2, 8, 0xffffff);
                    }
                });
                drawer.transform.x = x;
                addWidget(drawer);
                
                try {
                    ColorEditor ce = new ColorEditor(s);
                    ce.transform.x = x + 5;
                    ce.transform.width = 20;
                    ce.transform.height = 10;
                    addWidget(ce);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                x += 30;
            }
        }
        
        class ColorEditor extends InputBox {
            public ColorEditor(String sub) throws Exception {
                super(Color.class.getField(sub));
            }
            
            @Override
            public ComponentEditor getEditor() {
                return ColorBox.this.editor;
            }
            
            @Override Object getEditInstance() {
                try {
                    return ColorBox.this.getEditInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            
            @Override
            public boolean canUpdateValue() {
                return false;
            }
            
            public boolean setValue() {
                try {
                    double val = Double.valueOf(((TextBox)getComponent("TextBox")).content) / 255.0;
                    targetField.set(getEditInstance(), val);
                    return true;
                } catch(Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            
            @Override
            public void updateValue() {
                TextBox.get(this).content = String.valueOf( (int) (((double)TypeHelper.get(targetField, getEditInstance())) * 255) );
            }
        }

        @Override
        public void updateValue() {}

        @Override
        public boolean canUpdateValue() {
            return false;
        }
        
    }
    
    /**
     * Input box pref element editor. Default pref for most stuffs.
     */
    public static class InputBox extends ElementEditor {
        
        boolean inputDirty;
        
        long lastErrorTime = -1;
        
        public InputBox(Field f) {
            super(f);
            
            transform.x = 2;
            transform.width = 120;
            transform.height = 10;
            
            addComponent(new TextBox().setSize(9));
            regEventHandler(new FrameEventHandler() {
                @Override
                public void handleEvent(Widget w, FrameEvent event) {
                    if(lastErrorTime != -1 && GameTimer.getAbsTime() - lastErrorTime < 1000) {
                        GL11.glColor4d(1, 0, 0, GameTimer.getAbsTime() % 500 < 250 ? 0.6 : 0.3);
                    } else if(inputDirty) {
                        GL11.glColor4d(1, 0.6, 0, 0.3);
                    } else {
                        GL11.glColor4d(1, 1, 1, 0.3);
                    }
                    HudUtils.colorRect(0, 0, 
                        w.transform.width, w.transform.height);
                    GL11.glColor4d(1, 1, 1, 1);
                }
            });
            regEventHandler(new ChangeContentHandler() {
                @Override
                public void handleEvent(Widget w, ChangeContentEvent event) {
                    inputDirty = true;
                }
            });
            regEventHandler(new ConfirmInputHandler() {
                @Override
                public void handleEvent(Widget w, ConfirmInputEvent event) {
                    if(inputDirty) {
                        //Try to edit the edit target. if not successful, show error.
                        if(!setValue()) {
                            lastErrorTime = GameTimer.getAbsTime();
                        } else {
                            updateTargetWidget();
                            inputDirty = false;
                        }
                    }
                }
            });

        }
        
        public boolean setValue() {
            return tryEdit(((TextBox)getComponent("TextBox")).content);
        }

        @Override
        public void updateValue() {
            TextBox.get(this).content = TypeHelper.repr(targetField, getEditInstance());
        }

        @Override
        public boolean canUpdateValue() {
            return !inputDirty;
        }
        
    }
    
    public static class EnumSelector extends ElementEditor {
        
        Object[] enumConstants;
        
        TextBox box;
        
        Widget list;

        public EnumSelector(Field f) {
            super(f);
            enumConstants = f.getType().getEnumConstants();
            transform.setSize(70, 0);
            transform.setPos(60, -10);
        }
        
        @Override
        public void onAdded() {
            transform.setSize(65, 10);
            box = new TextBox().disallowEdit().setSize(10);
            addComponent(box);
            Tint tint = new Tint();
            tint.idleColor = new Color(1, 1, 1, 0.3);
            addComponent(tint);
            
            regEventHandler(new MouseDownHandler() {

                @Override
                public void handleEvent(Widget w, MouseDownEvent event) {
                    if(list != null)
                        list.dispose();
                    list = new Widget();
                    list.transform.setPos(0, 0).setSize(40, 10 * enumConstants.length);
                    for(int i = 0; i < enumConstants.length; ++i) {
                        list.addWidget(new Value(i));
                    }
                    w.addWidget(list);
                    list.gainFocus();
                    list.regEventHandler(new LostFocusHandler() {
                        @Override
                        public void handleEvent(Widget w, LostFocusEvent event) {
                            list.dispose();
                            list = null;
                        }
                    });
                }
                
            });
            
            super.onAdded();
        }

        @Override
        public void updateValue() {
            box.content = TypeHelper.repr(targetField, getEditInstance());
        }

        @Override
        public boolean canUpdateValue() {
            return true;
        }
        
        private class Value extends Widget {
            
            String name;
            
            public Value(int i) {
                transform.setSize(40, 10);
                transform.setPos(65, 10 * i);
                name = enumConstants[i].toString();
                addComponent(new TextBox().setSize(10).disallowEdit().setContent(name));
                Tint tint = new Tint();
                tint.idleColor = new Color(1, 1, 1, 0.3);
                addComponent(tint);
                
                regEventHandler(new MouseDownHandler() {
                    @Override
                    public void handleEvent(Widget w, MouseDownEvent event) {
                        TypeHelper.edit(targetField, getEditInstance(), name);
                        editor.widget.dirty = true;
                    }
                });
            }
        }
        
    }
}
