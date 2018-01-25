import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { DogComponent } from './dog.component';
import { DogDetailComponent } from './dog-detail.component';
import { DogPopupComponent } from './dog-dialog.component';
import { DogDeletePopupComponent } from './dog-delete-dialog.component';

export const dogRoute: Routes = [
    {
        path: 'dog',
        component: DogComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftApp.dog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dog/:id',
        component: DogDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftApp.dog.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dogPopupRoute: Routes = [
    {
        path: 'dog-new',
        component: DogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftApp.dog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dog/:id/edit',
        component: DogPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftApp.dog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dog/:id/delete',
        component: DogDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'craftApp.dog.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
