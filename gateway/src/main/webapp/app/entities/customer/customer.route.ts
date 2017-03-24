import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CustomerComponent } from './customer.component';
import { CustomerDetailComponent } from './customer-detail.component';
import { CustomerPopupComponent } from './customer-dialog.component';
import { CustomerDeletePopupComponent } from './customer-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class CustomerResolvePagingParams implements Resolve<any> {

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

export const customerRoute: Routes = [
  {
    path: 'customer',
    component: CustomerComponent,
    resolve: {
      'pagingParams': CustomerResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customer.home.title'
    }
  }, {
    path: 'customer/:id',
    component: CustomerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customer.home.title'
    }
  }
];

export const customerPopupRoute: Routes = [
  {
    path: 'customer-new',
    component: CustomerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customer.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'customer/:id/edit',
    component: CustomerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customer.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'customer/:id/delete',
    component: CustomerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'bssuiteGatewayApp.customer.home.title'
    },
    outlet: 'popup'
  }
];
