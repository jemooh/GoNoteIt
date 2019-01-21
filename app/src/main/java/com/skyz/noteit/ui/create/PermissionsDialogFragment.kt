package com.skyz.noteit.ui.create

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import com.skyz.noteit.R
import com.skyz.noteit.type.ReadAccess
import com.skyz.noteit.type.WriteAccess

class PermissionsDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_READ_PERMS = "read perms"
        private const val ARG_WRITE_PERMS = "write perms"

        fun newInstance(readPerms: ReadAccess, writePerms: WriteAccess): PermissionsDialogFragment {
            val args = Bundle()
            args.putSerializable(ARG_READ_PERMS, readPerms)
            args.putSerializable(ARG_WRITE_PERMS, writePerms)

            val fragment = PermissionsDialogFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var view = activity!!.layoutInflater.inflate(R.layout.dialog_privacy, null)
        displayReadPerms(view)
        displayWritePerms(view)
        var listener = activity as PermissionsDialogListener

        return AlertDialog.Builder(activity)
                .setTitle(R.string.permissions_dialog_title)
                .setPositiveButton(R.string.ok) { _, _ ->
                    run {
                        listener.onChangedReadPerms(getChosenReadPerm(view))
                        listener.onChangedWritePerms(getChosenWritePerm(view))
                        dismiss()
                    }
                }
                .setView(view)
                .create()
    }

    private fun displayReadPerms(view: View) {
        var readRadioGroup = view.findViewById<RadioGroup>(R.id.readPermsRadioGroup)
        readRadioGroup.setOnCheckedChangeListener { _, _ -> displayReadExplanation(view) }

        when (getReadPerms()) {
            ReadAccess.PUBLIC -> readRadioGroup.check(R.id.read_public_radio_button)
            ReadAccess.PRIVATE -> readRadioGroup.check(R.id.read_private_radio_button)
            ReadAccess.VIA_LINK -> readRadioGroup.check(R.id.read_via_link_radio_button)
        }
    }

    private fun getReadPerms() = arguments!!.getSerializable(ARG_READ_PERMS) as ReadAccess

    private fun displayWritePerms(view: View) {
        var writeRadioGroup = view.findViewById<RadioGroup>(R.id.writePermsRadioGroup)
        writeRadioGroup.setOnCheckedChangeListener { _, _ -> displayWriteExplanation(view) }

        when (getWritePerms()) {
            WriteAccess.EVERYONE -> writeRadioGroup.check(R.id.write_everyone_radio_button)
            WriteAccess.ONLY_OWNER -> writeRadioGroup.check(R.id.write_only_owner_radio_button)
        }
    }

    private fun getWritePerms() = arguments!!.getSerializable(ARG_WRITE_PERMS) as WriteAccess

    private fun displayReadExplanation(view: View) {
        view.findViewById<TextView>(R.id.readPermsExplanationTextView).text =
                        getReadPermsExplanation(context!!, getChosenReadPerm(view))
    }

    private fun displayWriteExplanation(view: View) {

        view.findViewById<TextView>(R.id.writePermsExplanationTextView).text =
                        getWritePermsExplanation(context!!, getChosenWritePerm(view))
    }

    private fun getChosenReadPerm(view: View): ReadAccess {
        var readRadioGroup = view.findViewById<RadioGroup>(R.id.readPermsRadioGroup)

        return when (readRadioGroup.checkedRadioButtonId) {
            R.id.read_public_radio_button -> ReadAccess.PUBLIC
            R.id.read_via_link_radio_button -> ReadAccess.VIA_LINK
            else -> ReadAccess.PRIVATE
        }
    }

    private fun getChosenWritePerm(view: View): WriteAccess {
        var writeRadioGroup = view.findViewById<RadioGroup>(R.id.writePermsRadioGroup)

        return when (writeRadioGroup.checkedRadioButtonId) {
            R.id.write_everyone_radio_button -> WriteAccess.EVERYONE
            else -> WriteAccess.ONLY_OWNER
        }
    }

    interface PermissionsDialogListener {

        fun onChangedReadPerms(readAccess: ReadAccess)

        fun onChangedWritePerms(writeAccess: WriteAccess)
    }
}

