import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Vote } from './vote.model';
import { VotePopupService } from './vote-popup.service';
import { VoteService } from './vote.service';
import { User, UserService } from '../../shared';
import { Dog, DogService } from '../dog';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vote-dialog',
    templateUrl: './vote-dialog.component.html'
})
export class VoteDialogComponent implements OnInit {

    vote: Vote;
    isSaving: boolean;

    users: User[];

    dogs: Dog[];
    createTSDp: any;
    updateTSDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private voteService: VoteService,
        private userService: UserService,
        private dogService: DogService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.dogService.query()
            .subscribe((res: ResponseWrapper) => { this.dogs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vote.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voteService.update(this.vote));
        } else {
            this.subscribeToSaveResponse(
                this.voteService.create(this.vote));
        }
    }

    private subscribeToSaveResponse(result: Observable<Vote>) {
        result.subscribe((res: Vote) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Vote) {
        this.eventManager.broadcast({ name: 'voteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackDogById(index: number, item: Dog) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vote-popup',
    template: ''
})
export class VotePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private votePopupService: VotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.votePopupService
                    .open(VoteDialogComponent as Component, params['id']);
            } else {
                this.votePopupService
                    .open(VoteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
