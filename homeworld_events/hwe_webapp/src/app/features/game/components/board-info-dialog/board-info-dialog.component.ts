import { Component, Inject } from "@angular/core";
import { MatButtonModule } from "@angular/material/button";
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from "@angular/material/dialog";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";

@Component({
  selector: 'app-board-info-dialog',
  standalone: true,
  imports: [ MatDialogModule, MatButtonModule ],
  templateUrl: './board-info-dialog.component.html',
  styleUrl: './board-info-dialog.component.css'
})
export class BoardInfoDialogComponent {
    boardInfoHtml: SafeHtml;
    readonly dialogTitle: string = 'Board Info';

    constructor(public boardInfoDialog: MatDialogRef<BoardInfoDialogComponent>, 
        @Inject(MAT_DIALOG_DATA) public boardInfoRaw: string,
        private sanitizer: DomSanitizer) {
            this.boardInfoHtml = this.sanitizer.bypassSecurityTrustHtml(boardInfoRaw);
    }

    /** Executed on Close button click from dialog window. */
    onClose(): void {
        this.boardInfoDialog.close();
    }
}