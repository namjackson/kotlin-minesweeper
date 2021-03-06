package minesweeper.domain

import minesweeper.domain.cell.MineGenerator
import minesweeper.domain.cell.toCell
import minesweeper.domain.cell.toMineCell
import minesweeper.domain.cell.toPosition

class MineMap(
    private val height: Int,
    private val width: Int,
    private val generator: MineGenerator = MineGenerator(height * width),
    private val mineMap: CellManager = CellManager.newInstance()
) {

    init {
        repeat(height * width, ::makeCell)
    }

    private fun makeCell(number: Int) {
        mineMap.addCell(makePosition(number).toCell())
    }

    private fun makePosition(number: Int) = Pair(number / width, number % height).toPosition()

    fun setMines(mineCount: Int) {
        generator.getRandomPosition(mineCount).map {
            mineMap.changeCell(makePosition(it).toMineCell())
        }
    }

    fun openAll() {
        mineMap.openAll()
    }

    fun getMapSize() = mineMap.size

    override fun toString() = mineMap.cells.groupBy { it.position.x }
        .values.joinToString("\n") { it.joinToString(" ") }
}
