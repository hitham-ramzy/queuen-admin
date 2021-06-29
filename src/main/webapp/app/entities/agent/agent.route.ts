import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AgentComponent } from './agent.component';
import { AgentDetailComponent } from './agent-detail.component';
import { AgentPopupComponent } from './agent-dialog.component';
import { AgentDeletePopupComponent } from './agent-delete-dialog.component';

@Injectable()
export class AgentResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const agentRoute: Routes = [
    {
        path: 'agent',
        component: AgentComponent,
        resolve: {
            'pagingParams': AgentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'agent/:id',
        component: AgentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const agentPopupRoute: Routes = [
    {
        path: 'agent-new',
        component: AgentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agent/:id/edit',
        component: AgentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'agent/:id/delete',
        component: AgentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.agent.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
