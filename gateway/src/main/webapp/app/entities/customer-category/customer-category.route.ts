import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerCategoryComponent } from './customer-category.component';
import { CustomerCategoryDetailComponent } from './customer-category-detail.component';
import { CustomerCategoryPopupComponent } from './customer-category-dialog.component';
import { CustomerCategoryDeletePopupComponent } from './customer-category-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerCategoryResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
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
    }
  }, {
    path: 'customer-category/:id',
    component: CustomerCategoryDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
    }
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
    outlet: 'popup'
  },
  {
    path: 'customer-category/:id/edit',
    component: CustomerCategoryPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'customer-category/:id/delete',
    component: CustomerCategoryDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customerCategory.home.title'
    },
    outlet: 'popup'
  }
];
