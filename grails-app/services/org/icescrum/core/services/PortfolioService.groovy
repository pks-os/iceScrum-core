/*
 * Copyright (c) 2017 Kagilum SAS.
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
 * Vincent Barrier (vbarrier@kagilum.com)
 * Nicolas Noullet (nnoullet@kagilum.com)
 */

package org.icescrum.core.services

import grails.transaction.Transactional
import org.icescrum.core.domain.Portfolio
import org.icescrum.core.domain.Project

@Transactional
class PortfolioService {

    def securityService

    void save(Portfolio portfolio, List<Project> projects) {
        portfolio.save()
        projects.each {
            portfolio.addToProjects(it)
        }
        portfolio.save(flush: true)
    }

    void delete(Portfolio portfolio) {
        portfolio.projects.each {
            portfolio.removeFromProjects(it)
        }
        portfolio.delete()
    }
}