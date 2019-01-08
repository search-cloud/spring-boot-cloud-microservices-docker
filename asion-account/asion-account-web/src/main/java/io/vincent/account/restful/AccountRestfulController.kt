package io.vincent.account.restful

import com.alibaba.dubbo.config.annotation.Reference
import io.vincent.account.domain.application.AccountManager
import io.vincent.account.domain.model.Account
import io.vincent.common.vo.Page
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Account rest controller.
 *
 * @author Vincent.
 * @since 16/5/29.
 */
@RestController
@RequestMapping("/accounts")
class AccountRestfulController {

    @Reference(version = "1.0.0", application = "\${dubbo.application.id}", url = "dubbo://192.168.10.94:9907")
    private val accountManager: AccountManager? = null

    /**
     * Page list accounts.
     *
     * @param pageNum current page number.
     * @param pageSize page size.
     * @return page of account list.
     */
    @GetMapping
    @ResponseBody
    fun list(@RequestParam pageNum: Int, @RequestParam pageSize: Int): Page<Account> {
        return accountManager!!.findPage(pageNum, pageSize)
    }

    /**
     * Create account.
     *
     * @param account account info.
     * @return created account.
     */
    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody account: Account): Account {
        return accountManager!!.create(account)
    }

    /**
     * Get account by id.
     *
     * @param id id of one account.
     * @return account.
     */
    @GetMapping("/{id}")
    @ResponseBody
    fun getOne(@PathVariable("id") id: Long): Account? {
        return accountManager!!.findOne(id)
    }

    /**
     * Update account by id.
     *
     * @param id id of the account.
     * @param account account need to be update.
     * @return account.
     */
    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable("id") id: Long, @RequestBody account: Account): Account {
        return accountManager!!.update(account)
    }

    /**
     * Delete account by id.
     *
     * @param id id of the account want to delete.
     * @return the id of deleted account.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun disable(@PathVariable("id") id: Long): Long? {
        return accountManager!!.disable(id)
    }

}
