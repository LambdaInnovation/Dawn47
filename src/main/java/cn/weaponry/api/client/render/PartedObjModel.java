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
package cn.weaponry.api.client.render;

import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.obj.GroupObject;
import net.minecraftforge.client.model.obj.WavefrontObject;

/**
 * @author WeAthFolD
 *
 */
public class PartedObjModel extends PartedModel {
    
    WavefrontObject obj;
    
    public PartedObjModel(WavefrontObject _obj) {
        obj = _obj;
        for(GroupObject groups : obj.groupObjects) {
            this.regPart(groups.name);
        }
    }

    @Override
    protected void renderAtCenter(String name) {
        obj.renderPart(name);
    }

}
