package org.icescrum.core.ui

import org.icescrum.core.artefact.AbstractScrumArtefactHandler

/*
 * Copyright (c) 2012 Kagilum SAS
 *
 * This file is part of iceScrum.
 *
 * iceScrum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * iceScrum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with iceScrum.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors:
 *
 * Nicolas Noullet (nnoullet@kagilum.com)
 *
 */

class UiDefinitionArtefactHandler extends AbstractScrumArtefactHandler {

    static public final String TYPE = 'UiDefinition'

    UiDefinitionArtefactHandler() {
        super(TYPE, UiDefinitionClass.class, DefaultUiDefinitionClass.class, TYPE)
    }
}
