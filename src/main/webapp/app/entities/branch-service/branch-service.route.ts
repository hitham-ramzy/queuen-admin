import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BranchServiceComponent } from './branch-service.component';
import { BranchServiceDetailComponent } from './branch-service-detail.component';
import { BranchServicePopupComponent } from './branch-service-dialog.component';
import { BranchServiceDeletePopupComponent } from './branch-service-delete-dialog.component';

@Injectable()
export class BranchServiceResolvePagingParams implements Resolve<any> {

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

export const branchServiceRoute: Routes = [
    {
        path: 'branch-service',
        component: BranchServiceComponent,
        resolve: {
            'pagingParams': BranchServiceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branchService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'branch-service/:id',
        component: BranchServiceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branchService.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const branchServicePopupRoute: Routes = [
    {
        path: 'branch-service-new',
        component: BranchServicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branchService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'branch-service/:id/edit',
        component: BranchServicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branchService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'branch-service/:id/delete',
        component: BranchServiceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branchService.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
