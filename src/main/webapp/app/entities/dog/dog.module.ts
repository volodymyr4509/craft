import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CraftSharedModule } from '../../shared';
import { CraftAdminModule } from '../../admin/admin.module';
import {
    DogService,
    DogPopupService,
    DogComponent,
    DogDetailComponent,
    DogDialogComponent,
    DogPopupComponent,
    DogDeletePopupComponent,
    DogDeleteDialogComponent,
    dogRoute,
    dogPopupRoute,
} from './';

const ENTITY_STATES = [
    ...dogRoute,
    ...dogPopupRoute,
];

@NgModule({
    imports: [
        CraftSharedModule,
        CraftAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DogComponent,
        DogDetailComponent,
        DogDialogComponent,
        DogDeleteDialogComponent,
        DogPopupComponent,
        DogDeletePopupComponent,
    ],
    entryComponents: [
        DogComponent,
        DogDialogComponent,
        DogPopupComponent,
        DogDeleteDialogComponent,
        DogDeletePopupComponent,
    ],
    providers: [
        DogService,
        DogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CraftDogModule {}
