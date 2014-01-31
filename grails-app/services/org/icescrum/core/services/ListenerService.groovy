/*
 * Copyright (c) 2014 Kagilum.
 *
 * This file is part of iceScrum.
 *
 * iceScrum is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * iceScrum is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with iceScrum.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Authors:
 *
 * Vincent Barrier (vbarrier@kagilum.com)
 * Nicolas Noullet (nnoullet@kagilum.com)
 *
 */
package org.icescrum.core.services

import grails.plugin.fluxiable.Activity
import org.icescrum.core.domain.Story
import org.icescrum.core.domain.User
import org.icescrum.core.event.IceScrumListener
import org.icescrum.core.event.IceScrumEventType

class ListenerService {

    def springSecurityService

    @IceScrumListener(domain='story', eventType=IceScrumEventType.CREATE)
    void storyCreate(Story story, Map dirtyProperties) {
        log.debug("the story $story.name has been created")
        def product = story.backlog
        def u = (User) springSecurityService.currentUser
        story.addActivity(u, Activity.CODE_SAVE, story.name)
        broadcast(function: 'add', message: story, channel:'product-'+product.id)
    }

    @IceScrumListener(domain='story', eventType=IceScrumEventType.UPDATE)
    void storyUpdate(Story story, Map dirtyProperties) {
        log.debug("the story $story.name has been updated")
        if (dirtyProperties) {
            def product = story.backlog
            ['feature', 'dependsOn'].each { property ->
                if (dirtyProperties.containsKey(property)) {
                    def oldProperty = dirtyProperties[property]
                    def newProperty = story."$property"
                    if (oldProperty != null) {
                        oldProperty.lastUpdated = new Date()
                        broadcast(function: 'update', message: oldProperty, channel:'product-'+product.id)
                    }
                    if (newProperty != null) {
                        newProperty.lastUpdated = new Date()
                        broadcast(function: 'update', message: newProperty, channel:'product-'+product.id)
                    }
                }
            }
            def u = (User) springSecurityService.currentUser
            story.addActivity(u, Activity.CODE_UPDATE, story.name)
            broadcast(function: 'update', message: story, channel:'product-'+product.id)
        }
    }

    @IceScrumListener(domain='story', eventType=IceScrumEventType.DELETE)
    void storyDelete(Story story, Map properties) {
        log.debug("the story $story.name has been deleted")
        def product = properties.backlog
        broadcast(function: 'delete', message: [class: story.class, id: properties.id, state: properties.state], channel:'product-'+product.id)
    }
}
