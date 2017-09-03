import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CustomerCategoryComponent } from './customer-category.component';
import { CustomerCategoryDetailComponent } from './customer-category-detail.component';
import { CustomerCategoryPopupComponent } from './customer-category-dialog.component';
import { CustomerCategoryDeletePopupComponent } from './customer-category-delete-dialog.component';

@Injectable()
export class CustomerCategoryResolvePagingParams implements Resolve<any> {

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

export const customerCategoryRoute: Routes = [
    {
        path: 'customer-category',
        component: CustomerCategoryComponent,
        resolve: {
            'pagingParams': CustomerCategoryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'customer-category/:id',
        component: CustomerCategoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const customerCategoryPopupRoute: Routes = [
    {
        path: 'customer-category-new',
        component: CustomerCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-category/:id/edit',
        component: CustomerCategoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'customer-category/:id/delete',
        component: CustomerCategoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
