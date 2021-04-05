package org.vaadin.example

import com.vaadin.flow.component.*
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dependency.CssImport
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.Push
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.PWA
import org.vaadin.example.data.Db
import org.vaadin.example.data.Person
import org.vaadin.example.data.PersonDao
import org.vaadin.example.view.BorderedField


/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Project Base for Vaadin", shortName = "Project Base", enableInstallPrompt = false)
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@Push
class MainView : VerticalLayout() {

    val textLastName = BorderedField("Last name",true)
    val textFirstName = BorderedField("First name",true)
    val textMidName = BorderedField("Middle name",false)
    val textPhone = BorderedField("Phone",false)

    val db=Db()
    val personDao=PersonDao(db)
    private val all=personDao.all()

    init {
        if(all.isNullOrEmpty())
            personDao.createTable()
        else {
            all.forEach {
                println(it.lastName+" "+it.firstName)
            }
        }

        val button = Button(
            "Save"
        ) { e: ClickEvent<Button?>? ->
            val vf=ValidatingFields(textLastName,textFirstName)
            if(vf.verify()) {
                personDao.insert(Person(lastName = textLastName.value,
                    firstName = textFirstName.value,
                    midName = textMidName.value,
                    phone = textPhone.value
                ))
            } else
                Notification.show(vf.errorMessage(), 5000, Notification.Position.MIDDLE)
        }

        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY)

        button.addClickShortcut(Key.ENTER)

        addClassName("centered-content")

        add(textLastName, textFirstName, textMidName, textPhone, button)

    }

    override fun onAttach(attachEvent: AttachEvent) {
        super.onAttach(attachEvent)
        val ui = attachEvent.ui

    }

    override fun onDetach(detachEvent: DetachEvent?) {
        super.onDetach(detachEvent)
        db.close()
    }

}

