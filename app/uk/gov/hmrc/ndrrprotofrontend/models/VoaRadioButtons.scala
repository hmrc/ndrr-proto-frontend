/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ndrrprotofrontend.models

import play.api.data.Form
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.Aliases.{Fieldset, Legend, Text, RadioItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.errormessage.ErrorMessage
import uk.gov.hmrc.govukfrontend.views.viewmodels.radios.Radios

case class VoaRadioName(key: String)
case class VoaRadioHeader(title: String, classes: String , isPageHeading: Boolean)
case class VoaRadioButtons(radioContent: String, radioValue: RadioEntry)

case class VoaRadios(radioGroupName: VoaRadioName, voaRadionButtons: Seq[VoaRadioButtons], voaTitle: Option[VoaRadioHeader] = None)

object VoaRadios {

  def buildRadios[A](
                  form:Form[A],
                  voaRadios: VoaRadios
                 )(implicit messages: Messages): Radios = {
    Radios(
      fieldset = voaRadios.voaTitle.map(header =>
        Fieldset(
          legend = Some(Legend(
            content = Text(Messages(header.title)),
            classes = header.classes,
            isPageHeading = header.isPageHeading
          ))
        )
      ),
      idPrefix = Some(voaRadios.radioGroupName.key),
      name = voaRadios.radioGroupName.key,
      items = voaRadios.voaRadionButtons.map { item =>
        RadioItem(
          content = Text(Messages(item.radioContent)),
          value = Some(item.radioValue.toString),
          checked = form.data.values.toList.contains(item.radioValue)
        )
      },
      classes = "govuk-radios",
      errorMessage = form(voaRadios.radioGroupName.key).error.map(err => ErrorMessage(content = Text(messages(err.message)))),

    )
  }
}

