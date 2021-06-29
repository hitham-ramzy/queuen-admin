import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WindowComponent } from './window.component';
import { WindowDetailComponent } from './window-detail.component';
import { WindowPopupComponent } from './window-dialog.component';
import { WindowDeletePopupComponent } from './window-delete-dialog.component';

@Injectable()
export class WindowResolvePagingParams implements Resolve<any> {

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

export const windowRoute: Routes = [
    {
        path: 'window',
        component: WindowComponent,
        resolve: {
            'pagingParams': WindowResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.window.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'window/:id',
        component: WindowDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.window.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const windowPopupRoute: Routes = [
    {
        path: 'window-new',
        component: WindowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.window.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'window/:id/edit',
        component: WindowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.window.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'window/:id/delete',
        component: WindowDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.window.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
