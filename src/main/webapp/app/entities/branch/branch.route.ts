import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BranchComponent } from './branch.component';
import { BranchDetailComponent } from './branch-detail.component';
import { BranchPopupComponent } from './branch-dialog.component';
import { BranchDeletePopupComponent } from './branch-delete-dialog.component';

@Injectable()
export class BranchResolvePagingParams implements Resolve<any> {

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

export const branchRoute: Routes = [
    {
        path: 'branch',
        component: BranchComponent,
        resolve: {
            'pagingParams': BranchResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'branch/:id',
        component: BranchDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branch.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const branchPopupRoute: Routes = [
    {
        path: 'branch-new',
        component: BranchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'branch/:id/edit',
        component: BranchPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'branch/:id/delete',
        component: BranchDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'queueNApp.branch.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
