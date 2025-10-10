import { Component, computed, effect, inject, OnInit } from "@angular/core";
import { EventStore } from "../game/data/event-store.service";
import { DomSanitizer, SafeHtml } from "@angular/platform-browser";
import { HttpClient } from "@angular/common/http";
import { ERROR_HTML } from "../../shared/constant/common-constant";

@Component({
  selector: 'app-rules',
  standalone: true,
  imports: [],
  templateUrl: './rules.component.html',
  styleUrls: [ './rules.component.css' ]
})
export class RulesComponent {
}