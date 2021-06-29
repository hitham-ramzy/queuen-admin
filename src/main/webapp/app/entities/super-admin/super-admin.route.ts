import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SuperAdminComponent } from './super-admin.component';
import { SuperAdminDetailComponent } from './super-admin-detail.component';
import { SuperAdminPopupComponent } from './super-admin-dialog.component';
import { SuperAdminDeletePopupComponent } from './super-admin-delete-dialog.component';

@Injectable()
export class SuperAdminResolvePagingParams implements Resolve<any> {

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

export const superAdminRoute: Routes = [
    {
        path: 'super-admin',
        component: SuperAdminComponent,
        resolve: {
            'pagingParams': SuperAdminResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.superAdmin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'super-admin/:id',
        component: SuperAdminDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.superAdmin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const superAdminPopupRoute: Routes = [
    {
        path: 'super-admin-new',
        component: SuperAdminPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.superAdmin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'super-admin/:id/edit',
        component: SuperAdminPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.superAdmin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'super-admin/:id/delete',
        component: SuperAdminDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.superAdmin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
